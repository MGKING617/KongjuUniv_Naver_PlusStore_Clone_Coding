import React, { useEffect, useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';

function CartPage() {
    const { user } = useContext(AuthContext);
    const [cartItems, setCartItems] = useState([]);
    const navigate = useNavigate();

    // 장바구니 불러오기
    useEffect(() => {
        if (!user) {
            alert("로그인이 필요합니다.");
            navigate('/login');
            return;
        }
        fetch(`http://localhost:8080/api/shop/cart/${user.id}`)
            .then(res => res.json())
            .then(data => setCartItems(data))
            .catch(err => console.error(err));
    }, [user, navigate]);

    // 🟢 [추가됨] 장바구니 삭제 핸들러
    const handleDelete = (optionId) => {
        if (!window.confirm("정말 삭제하시겠습니까?")) return;

        fetch(`http://localhost:8080/api/shop/cart/${user.id}/${optionId}`, {
            method: 'DELETE',
        })
            .then(res => {
                if (res.ok) {
                    // 화면에서도 바로 삭제 (새로고침 없이)
                    setCartItems(prev => prev.filter(item => item.optionId !== optionId));
                    alert("삭제되었습니다.");
                } else {
                    alert("삭제 실패");
                }
            })
            .catch(err => console.error("삭제 중 오류:", err));
    };

    const handleOrder = () => {
        alert("주문 기능은 준비 중입니다.");
    };

    const totalPrice = cartItems.reduce((acc, item) => acc + (item.price * item.quantity), 0);

    return (
        <div style={{ maxWidth: '800px', margin: '40px auto', padding: '20px' }}>
            <h2>🛒 장바구니</h2>

            {cartItems.length === 0 ? (
                <p style={{ textAlign: 'center', padding: '50px', color: '#888', background:'#f9f9f9', borderRadius:'10px' }}>
                    장바구니가 비어있습니다.
                </p>
            ) : (
                <>
                    <table style={{ width: '100%', borderCollapse: 'collapse', marginTop: '20px' }}>
                        <thead>
                        <tr style={{ borderBottom: '2px solid #333', textAlign: 'left', background:'#f4f4f4' }}>
                            <th style={{ padding: '10px' }}>상품정보</th>
                            <th style={{ padding: '10px' }}>옵션</th>
                            <th style={{ padding: '10px' }}>수량</th>
                            <th style={{ padding: '10px' }}>가격</th>
                            <th style={{ padding: '10px', textAlign:'center' }}>관리</th>
                        </tr>
                        </thead>
                        <tbody>
                        {cartItems.map((item, idx) => (
                            <tr key={idx} style={{ borderBottom: '1px solid #eee' }}>
                                <td style={{ padding: '15px 10px', display:'flex', alignItems:'center', gap:'10px' }}>
                                    <img src={item.imageUrl || "https://via.placeholder.com/50"} style={{width:'50px', height:'50px', objectFit:'cover', borderRadius:'4px', border:'1px solid #ddd'}} />
                                    <span style={{fontWeight:'500'}}>{item.productName}</span>
                                </td>
                                <td style={{ padding: '15px 10px', color: '#666', fontSize:'14px' }}>{item.optionName}</td>
                                <td style={{ padding: '15px 10px' }}>{item.quantity}개</td>
                                <td style={{ padding: '15px 10px', fontWeight: 'bold', color:'#333' }}>
                                    {(item.price * item.quantity).toLocaleString()}원
                                </td>
                                <td style={{ padding: '15px 10px', textAlign:'center' }}>
                                    {/* 🟢 삭제 버튼 */}
                                    <button
                                        onClick={() => handleDelete(item.optionId)}
                                        style={{background:'#fff', border:'1px solid #ddd', padding:'5px 10px', borderRadius:'4px', cursor:'pointer', color:'#d0021b', fontSize:'12px'}}
                                    >
                                        삭제
                                    </button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>

                    <div style={{ marginTop: '30px', textAlign: 'right', padding:'20px', background:'#f8f9fa', borderRadius:'5px' }}>
                        <span style={{fontSize:'16px', marginRight:'10px'}}>총 결제금액</span>
                        <strong style={{ color: '#03c75a', fontSize: '28px' }}>{totalPrice.toLocaleString()}원</strong>
                    </div>

                    <button
                        onClick={handleOrder}
                        style={{
                            width: '100%', marginTop: '20px', padding: '18px',
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