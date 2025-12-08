import React, { useEffect, useState, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext'; // 🟢 AuthContext 추가

function ProductDetail() {
    const { id } = useParams();
    const navigate = useNavigate();
    const { user } = useContext(AuthContext); // 🟢 로그인한 유저 정보 가져오기

    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [selectedOption, setSelectedOption] = useState("");
    const [quantity, setQuantity] = useState(1); // 🟢 수량 상태 추가

    useEffect(() => {
        fetch(`http://localhost:8080/api/products/${id}`)
            .then(res => {
                if (!res.ok) throw new Error("상품 없음");
                return res.json();
            })
            .then(data => {
                setProduct(data);
                setLoading(false);
            })
            .catch(err => {
                console.error(err);
                setLoading(false);
            });
    }, [id]);

    // 장바구니 담기 핸들러
    const handleAddToCart = () => {
        // 1. 로그인 체크
        if (!user) {
            alert("로그인이 필요한 서비스입니다.");
            navigate('/login');
            return;
        }
        // 2. 옵션 선택 체크
        if (!selectedOption) {
            alert("옵션을 선택해주세요.");
            return;
        }

        // 3. 서버 전송
        fetch("http://localhost:8080/api/shop/cart", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                customerId: user.id, // 🟢 로그인한 유저 ID 사용
                optionId: selectedOption,
                quantity: quantity
            })
        })
            .then(res => {
                if (res.ok) {
                    if(window.confirm("장바구니에 담겼습니다. 확인하시겠습니까?")) {
                        navigate('/cart');
                    }
                } else {
                    alert("장바구니 담기 실패");
                }
            })
            .catch(err => {
                console.error("장바구니 에러:", err);
                alert("서버 통신 오류");
            });
    };

    const handleBuyNow = () => {
        alert("바로구매 기능은 아직 구현되지 않았습니다.");
    };

    if (loading) return <div style={{padding:'50px', textAlign:'center'}}>상품 로딩 중...</div>;
    if (!product) return <div style={{padding:'50px', textAlign:'center'}}>존재하지 않는 상품입니다.</div>;

    return (
        <div style={{ maxWidth: '1000px', margin: '40px auto', display: 'flex', gap: '40px', padding:'20px' }}>
            <div style={{ flex: 1 }}>
                <div style={{ width: '100%', height: '400px', background: '#f4f4f4', borderRadius: '10px', display:'flex', alignItems:'center', justifyContent:'center', overflow:'hidden'}}>
                    {product.imageUrl ? (
                        <img src={product.imageUrl} alt={product.name} style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                    ) : (
                        <span style={{color:'#ccc', fontSize:'20px'}}>이미지 없음</span>
                    )}
                </div>
            </div>

            <div style={{ flex: 1 }}>
                <p style={{ fontSize: '14px', color: '#777', marginBottom:'5px' }}>
                    {product.seller?.storeName || "판매자 정보 없음"} &gt; {product.category?.categoryName || "기타"}
                </p>
                <h2 style={{fontSize:'28px', margin:'0 0 20px 0'}}>{product.name}</h2>
                <p style={{ fontSize: '30px', fontWeight: 'bold', color: '#03c75a' }}>
                    {product.price.toLocaleString()}원
                </p>

                {/* 옵션 선택 */}
                <div style={{margin:'20px 0', padding:'20px', background:'#f9f9f9', borderRadius:'5px', border:'1px solid #eee'}}>
                    <label style={{fontWeight:'bold', display:'block', marginBottom:'10px'}}>옵션 선택</label>
                    <select
                        style={{width:'100%', padding:'10px', border:'1px solid #ddd', borderRadius:'4px'}}
                        onChange={e=>setSelectedOption(e.target.value)}
                        value={selectedOption}
                    >
                        <option value="">-- [필수] 옵션을 선택하세요 --</option>
                        {product.options && product.options.map(opt => {
                            const priceDiff = opt.extraPrice > 0 ? ` (+${opt.extraPrice.toLocaleString()}원)`
                                : opt.extraPrice < 0 ? ` (${opt.extraPrice.toLocaleString()}원)` : "";
                            return (
                                <option key={opt.optionId} value={opt.optionId} disabled={opt.stock <= 0}>
                                    {opt.optionName}{priceDiff} {opt.stock <= 0 ? "(품절)" : ""}
                                </option>
                            );
                        })}
                    </select>

                    {/* 수량 선택 */}
                    <div style={{marginTop:'15px', display:'flex', alignItems:'center', gap:'10px'}}>
                        <span style={{fontWeight:'bold'}}>수량</span>
                        <input
                            type="number"
                            min="1"
                            value={quantity}
                            onChange={(e) => setQuantity(Number(e.target.value))}
                            style={{width:'60px', padding:'5px'}}
                        />
                    </div>
                </div>

                {/* 버튼 그룹 */}
                <div style={{display:'flex', gap:'10px', marginTop:'30px'}}>
                    <button onClick={handleAddToCart} style={{flex:1, padding:'15px', border:'1px solid #ddd', background:'white', borderRadius:'5px', fontWeight:'bold', cursor:'pointer', fontSize:'16px'}}>
                        장바구니
                    </button>
                    <button onClick={handleBuyNow} style={{flex:1, padding:'15px', border:'none', background:'#03c75a', color:'white', borderRadius:'5px', fontWeight:'bold', cursor:'pointer', fontSize:'16px'}}>
                        구매하기
                    </button>
                </div>
            </div>
        </div>
    );
}

export default ProductDetail;