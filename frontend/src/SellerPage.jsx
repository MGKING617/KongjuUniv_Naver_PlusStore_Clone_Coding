import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './App.css';

function SellerPage() {
    const navigate = useNavigate();

    // 입력값 상태
    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState('');

    // 옵션 상태 (기본 1개)
    const [options, setOptions] = useState([
        { optionName: '기본', extraPrice: 0, stock: 100 }
    ]);

    // 상품 등록 버튼 클릭
    const handleSubmit = () => {
        if (!name || !price || !stock) {
            alert("상품 정보를 모두 입력해주세요.");
            return;
        }

        const requestBody = {
            sellerId: 1, // DB에 있는 판매자 ID (1번)
            name: name,
            price: parseInt(price),
            stock: parseInt(stock),
            options: options.map(opt => ({
                optionName: opt.optionName,
                extraPrice: parseInt(opt.extraPrice),
                stock: parseInt(opt.stock)
            }))
        };

        fetch('http://localhost:8080/api/seller/products', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(requestBody)
        })
            .then(res => {
                if (res.ok) {
                    alert('상품 등록 성공! 🎉');
                    navigate('/'); // 메인으로 이동해서 확인
                } else {
                    alert('등록 실패.. 백엔드 로그를 확인하세요.');
                }
            })
            .catch(err => {
                console.error(err);
                alert("서버 연결 실패");
            });
    };

    return (
        <div className="seller-page" style={{maxWidth: '600px', margin: '0 auto', padding: '20px'}}>
            <h2>📦 판매자 상품 등록</h2>

            <div className="seller-info" style={{background: '#f9f9f9', padding: '20px', borderRadius: '10px', marginBottom: '20px'}}>
                <h3>기본 정보</h3>
                <input type="text" placeholder="상품명" value={name} onChange={(e)=>setName(e.target.value)} style={{display:'block', width:'100%', padding:'10px', marginBottom:'10px'}} />
                <input type="number" placeholder="가격" value={price} onChange={(e)=>setPrice(e.target.value)} style={{display:'block', width:'100%', padding:'10px', marginBottom:'10px'}} />
                <input type="number" placeholder="재고" value={stock} onChange={(e)=>setStock(e.target.value)} style={{display:'block', width:'100%', padding:'10px', marginBottom:'10px'}} />
            </div>

            <div className="seller-info" style={{background: '#f9f9f9', padding: '20px', borderRadius: '10px', marginBottom: '20px'}}>
                <h3>옵션 정보</h3>
                {options.map((opt, idx) => (
                    <div key={idx} style={{display:'flex', gap:'5px', marginBottom:'5px'}}>
                        <input type="text" placeholder="옵션명" value={opt.optionName} onChange={(e)=>{
                            const copy = [...options]; copy[idx].optionName = e.target.value; setOptions(copy);
                        }} style={{flex:2, padding:'8px'}} />
                        <input type="number" placeholder="추가금" value={opt.extraPrice} onChange={(e)=>{
                            const copy = [...options]; copy[idx].extraPrice = e.target.value; setOptions(copy);
                        }} style={{flex:1, padding:'8px'}} />
                        <input type="number" placeholder="수량" value={opt.stock} onChange={(e)=>{
                            const copy = [...options]; copy[idx].stock = e.target.value; setOptions(copy);
                        }} style={{flex:1, padding:'8px'}} />
                    </div>
                ))}
            </div>

            <button className="add-btn" onClick={handleSubmit} style={{width:'100%', padding:'15px', background:'#03c75a', color:'white', border:'none', borderRadius:'5px', fontSize:'18px', fontWeight:'bold', cursor:'pointer'}}>
                상품 등록하기
            </button>
        </div>
    );
}

export default SellerPage;