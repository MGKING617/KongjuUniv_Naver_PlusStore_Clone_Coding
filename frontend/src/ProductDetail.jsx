import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

function ProductDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  
  // 상태 관리 (데이터, 옵션, 수량)
  const [product, setProduct] = useState(null);
  const [selectedOption, setSelectedOption] = useState("");
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true); // 로딩 상태 추가

  // 임시 고객 ID (로그인 기능 완성 전까지 1번 사용)
  const customerId = 1; 

  // 1. 백엔드에서 상품 정보 가져오기
  useEffect(() => {
    fetch(`http://localhost:8080/api/products/${id}`)
      .then(res => {
        if (!res.ok) {
            throw new Error("서버에서 상품 정보를 가져오지 못했습니다.");
        }
        return res.json();
      })
      .then(data => {
          setProduct(data);
          setLoading(false);
      })
      .catch(err => {
          console.error("에러 발생:", err);
          alert("상품 정보를 불러오는 중 문제가 발생했습니다.");
          setLoading(false);
      });
  }, [id]);

  // 2. 장바구니 담기 버튼 클릭
  const handleAddToCart = () => {
    if (!selectedOption) {
      alert("⚠️ 옵션을 먼저 선택해주세요!");
      return;
    }

    fetch("http://localhost:8080/api/shop/cart", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        customerId: customerId,
        optionId: selectedOption,
        quantity: quantity
      })
    })
    .then(res => {
      if (res.ok) {
        if (window.confirm("장바구니에 담겼습니다! 🛒\n장바구니로 이동하시겠습니까?")) {
          navigate("/cart");
        }
      } else {
        alert("장바구니 담기 실패 😢");
      }
    })
    .catch(err => console.error("통신 에러:", err));
  };

  // 로딩 중이거나 데이터가 없을 때 표시
  if (loading) return <div style={{ textAlign: 'center', padding: '50px' }}>상품 로딩 중...</div>;
  if (!product) return <div style={{ textAlign: 'center', padding: '50px' }}>상품 정보가 없습니다.</div>;

  return (
    <div className="product-detail">
      <div className="detail-left">
        {/* 이미지가 없으면 기본 이미지 사용 */}
        <img src={product.img || "https://via.placeholder.com/400"} alt="상품 이미지" />
      </div>

      <div className="detail-right">
        {/* 판매자 이름 (안전하게 표시) */}
        <p style={{ fontSize: '14px', color: '#777' }}>
            {product.sellerName ? product.sellerName : "판매자 정보 없음"}
        </p>
        
        <h2>{product.name}</h2>
        
        <p className="price">
          {/* 가격 표시 안전장치 (데이터 없으면 0원) */}
          {product.price ? product.price.toLocaleString() : 0}원
        </p>

        {/* --- 옵션 선택 영역 --- */}
        <div className="option-area" style={{ margin: '20px 0' }}>
          <h4>옵션 선택</h4>
          <select 
            style={{ width: '100%', padding: '10px', marginTop: '5px' }}
            onChange={(e) => setSelectedOption(e.target.value)}
            value={selectedOption}
          >
            <option value="">-- [필수] 옵션을 선택해주세요 --</option>
            {product.options && product.options.map(opt => (
              <option key={opt.id} value={opt.id} disabled={opt.stock <= 0}>
                {opt.optionName} (+{opt.extraPrice}원) {opt.stock <= 0 ? "(품절)" : ""}
              </option>
            ))}
          </select>
        </div>

        {/* --- 수량 선택 영역 --- */}
        <div className="quantity-area" style={{ marginBottom: '20px' }}>
            <h4>수량</h4>
            <input 
                type="number" 
                min="1" 
                value={quantity}
                onChange={(e) => setQuantity(Number(e.target.value))}
                style={{ padding: '10px', width: '60px' }}
            />
        </div>

        {/* --- 버튼 영역 --- */}
        <div className="btn-group" style={{ display: 'flex', gap: '10px' }}>
            <button 
                onClick={handleAddToCart}
                className="cart-btn"
                style={{
                    flex: 1, padding: '15px', background: 'white',
                    color: '#03c75a', border: '1px solid #03c75a',
                    fontWeight: 'bold', cursor: 'pointer', borderRadius: '5px'
                }}
            >
                장바구니 담기
            </button>
            <button
                className="buy-btn"
                style={{
                    flex: 1, padding: '15px', background: '#03c75a',
                    color: 'white', border: 'none',
                    fontWeight: 'bold', cursor: 'pointer', borderRadius: '5px'
                }}
            >
                바로 구매하기
            </button>
        </div>
      </div>
    </div>
  );
}

export default ProductDetail;