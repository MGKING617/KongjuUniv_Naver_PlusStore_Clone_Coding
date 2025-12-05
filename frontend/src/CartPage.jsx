import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

function CartPage() {
  const [cartItems, setCartItems] = useState([]);
  const navigate = useNavigate();
  const customerId = 1; // 임시로 1번 고객이라고 가정

  // 장바구니 목록 불러오기
  useEffect(() => {
    fetch(`http://localhost:8080/api/shop/cart/${customerId}`)
      .then(res => res.json())
      .then(data => setCartItems(data))
      .catch(err => console.error("장바구니 불러오기 실패:", err));
  }, []);

  // 주문하기 버튼 클릭
  const handleOrder = () => {
    fetch("http://localhost:8080/api/shop/order", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ customerId: customerId })
    })
    .then(res => {
        if (res.ok) return res.text();
        throw new Error("주문 실패");
    })
    .then(orderId => {
        alert(`주문이 완료되었습니다! (주문번호: ${orderId})`);
        setCartItems([]); // 장바구니 비우기
        navigate("/"); // 메인으로 이동
    })
    .catch(err => alert("주문 중 오류가 발생했습니다."));
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
                <th style={{ padding: '10px' }}>상품명</th>
                <th style={{ padding: '10px' }}>옵션</th>
                <th style={{ padding: '10px' }}>수량</th>
                <th style={{ padding: '10px' }}>가격</th>
              </tr>
            </thead>
            <tbody>
              {cartItems.map((item, idx) => (
                <tr key={idx} style={{ borderBottom: '1px solid #eee' }}>
                  <td style={{ padding: '15px 10px' }}>{item.productName}</td>
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