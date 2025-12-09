import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function MainPage() {
    const [sellers, setSellers] = useState([]);

    useEffect(() => {

        fetch("http://localhost:8080/api/sellers")
            .then(res => {
                if (!res.ok) throw new Error("네트워크 응답 실패");
                return res.json();
            })
            .then(data => {
                console.log("DB에서 가져온 판매자 목록:", data);
                setSellers(data);
            })
            .catch(err => console.error("스토어 로드 에러:", err));
    }, []);

    return (
        <div style={{maxWidth: '1200px', margin: '0 auto'}}>
            {/* 배너 섹션 */}
            <section className="main-banner" style={{height:'300px', background:'#03c75a', display:'flex', alignItems:'center', justifyContent:'center', color:'white', textAlign:'center'}}>
                <div>
                    <h2 style={{fontSize:'40px', marginBottom:'10px'}}>플러스 스토어 클론코딩</h2>
                </div>
            </section>
            {/* 스토어 목록 섹션 */}
            <section className="product-section" style={{padding:'20px'}}>
                <h3 style={{fontSize:'24px', marginBottom:'20px', borderBottom:'2px solid #333', paddingBottom:'10px'}}>입점 스토어 둘러보기</h3>

                {sellers.length === 0 ? (
                    <div style={{textAlign:'center', padding:'100px', color:'#999', fontSize:'18px', background:'#f9f9f9', borderRadius:'10px'}}>
                        <p>아직 등록된 스토어가 없습니다.</p>
                        <p style={{fontSize:'14px', marginTop:'10px'}}>판매자로 가입하여 첫 번째 스토어를 등록해보세요!</p>
                    </div>
                ) : (
                    <div style={{display:'grid', gridTemplateColumns:'repeat(4, 1fr)', gap:'20px'}}>
                        {sellers.map(seller => (
                            <Link key={seller.sellerId} to={`/store/${seller.sellerId}`} style={{textDecoration:'none', color:'black', border:'1px solid #eee', borderRadius:'10px', padding:'30px 20px', textAlign:'center', display:'block', transition:'transform 0.2s, box-shadow 0.2s', background:'white'}}>
                                <div style={{height:'80px', width:'80px', background:'#e8f5e9', display:'flex', alignItems:'center', justifyContent:'center', borderRadius:'50%', margin:'0 auto 20px auto'}}>
                                    <span style={{fontSize:'30px'}}>🏪</span>
                                </div>
                                {/* DB의 storeName 필드 표시 */}
                                <p style={{fontSize:'18px', fontWeight:'bold', marginBottom:'10px', color:'#333'}}>
                                    {seller.storeName || "이름 없는 스토어"}
                                </p>
                                <button style={{padding:'8px 25px', background:'#03c75a', color:'white', border:'none', borderRadius:'20px', cursor:'pointer', fontWeight:'bold', fontSize:'14px'}}>
                                    상점 입장
                                </button>
                            </Link>
                        ))}
                    </div>
                )}
            </section>
        </div>
    );
}

export default MainPage;