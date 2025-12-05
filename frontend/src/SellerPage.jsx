// SellerPage.js
import React from 'react';
import './App.css';

function SellerPage() {
  return (
    <div className="seller-page">
      <h2>판매자 센터</h2>

      <div className="seller-info">
        <h3>판매자 정보</h3>
        <p>스토어 이름: 네이버 스토어</p>
        <p>담당자: 홍길동</p>
      </div>

      <h3>등록 상품 목록</h3>
      <div className="product-list seller">
        <div className="product-card">
          <img src="https://via.placeholder.com/150" alt="상품1" />
          <p className="product-name">상품 A</p>
          <p className="product-price">19,900원</p>
        </div>
        <div className="product-card">
          <img src="https://via.placeholder.com/150" alt="상품2" />
          <p className="product-name">상품 B</p>
          <p className="product-price">29,900원</p>
        </div>
      </div>

      <button className="add-btn">상품 추가</button>
    </div>
  );
}

export default SellerPage;
