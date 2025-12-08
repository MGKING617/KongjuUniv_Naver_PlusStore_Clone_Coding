import React, { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';

function CartPage() {
    const { user } = useContext(AuthContext); // 로그인 정보 가져오기
    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();

    // 장바구니 목록 불러오기
    useEffect(() => {
        if (!user) {
            alert("로그인이 필요합니다.");
            navigate('/login');
            return;
        }

        // 🟢 로그인한 유저 ID(user.id)로 장바구니 조회
        fetch(`http://localhost:8080/api/shop/cart/${user.id}`)
            .then(res => res.json())
            .then(data => {
                console.log("장바구니 데이터:", data);
                setCartItems(data);
            })
            .catch(err => console.error("장바구니 불러오기 실패:", err));
    }, [user, navigate]);

    const handleOrder = () => {
        alert("주문 기능은 준비 중입니다.");
    };

    // 총 가격 계산
    const totalPrice = cartItems.reduce((acc, item) => acc + (item.price * item.quantity), 0);

    return (
        <div style={{ maxWidth: '800px', margin: '40px auto', padding: '20px' }}>
            <h2>🛒 장바구니</h2>

            {cartItems.length === 0 ? (
                <p style={{ textAlign: 'center', padding: '50px', color: '#888' }}>
                    장바구니가 비어있습니다.
                </p>
            ) : (
                <>
                    <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                        <thead>
                        <tr style={{ borderBottom: '2px solid #333', textAlign: 'left' }}>
                            <th style={{ padding: '10px' }}>상품정보</th>
                            <th style={{ padding: '10px' }}>옵션</th>
                            <th style={{ padding: '10px' }}>수량</th>
                            <th style={{ padding: '10px' }}>가격</th>
                        </tr>
                        </thead>
                        <tbody>
                        {cartItems.map((item, idx) => (
                            <tr key={idx} style={{ borderBottom: '1px solid #eee' }}>
                                <td style={{ padding: '15px 10px', display:'flex', alignItems:'center', gap:'10px' }}>
                                    {/* 이미지 표시 */}
                                    <img src={item.imageUrl || "https://via.placeholder.com/50"} style={{width:'50px', height:'50px', objectFit:'cover', borderRadius:'4px'}} />
                                    {item.productName}
                                </td>
                                <td style={{ padding: '15px 10px', color: '#666' }}>{item.optionName}</td>
                                <td style={{ padding: '15px 10px' }}>{item.quantity}개</td>
                                <td style={{ padding: '15px 10px', fontWeight: 'bold' }}>
                                    {(item.price * item.quantity).toLocaleString()}원
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <div style={{ marginTop: '30px', textAlign: 'right', fontSize: '20px' }}>
                        총 결제금액: <strong style={{ color: '#03c75a', fontSize: '24px' }}>{totalPrice.toLocaleString()}원</strong>
                    </div>

                    <button
                        onClick={handleOrder}
                        style={{
                            width: '100%', marginTop: '20px', padding: '15px',
                            background: '#03c75a', color: 'white', border: 'none',
                            fontSize: '18px', fontWeight: 'bold', cursor: 'pointer', borderRadius: '5px'
                        }}>
                        주문하기
                    </button>
                </>
            )}
        </div>
    );
}

export default CartPage;