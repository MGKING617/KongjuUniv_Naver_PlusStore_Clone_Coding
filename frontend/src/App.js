// App.js
import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';
import SellerPage from './SellerPage';
import LoginPage from './LoginPage';
import ProductDetail from './ProductDetail';

function App() {
  return (
    <Router>
      <div className="app">

        <header className="header">
          <div className="logo">N Pay</div>
          <nav className="nav">
            <Link to="/">메인</Link>
            <Link to="/seller">판매자센터</Link>
            <Link to="/login">로그인</Link>
          </nav>
        </header>

        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/seller" element={<SellerPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/product/:id" element={<ProductDetail />} />
        </Routes>

        <footer className="footer">
          © NAVER Corp.
        </footer>
      </div>
    </Router>
  );
}

// 메인 페이지 UI 분리
function MainPage() {
  const products = [
    { id: 1, name: '스칸디무드 옷장', price: 79900, img: "https://via.placeholder.com/300" },
    { id: 2, name: '모던 책상', price: 55900, img: "https://via.placeholder.com/300" },
    { id: 3, name: '화장대 세트', price: 129900, img: "https://via.placeholder.com/300" }
  ];

  return (
    <>
      <section className="main-banner">
        <div className="banner-content">
          <h2>네이버플러스 멤버십</h2>
          <p>쇼핑 혜택을 한 곳에서!</p>
          <button className="join-btn">가입하고 혜택받기</button>
        </div>
      </section>

      <section id="hot" className="product-section">
        <h3>인기 상품</h3>
        <div className="product-list">
          {products.map(p => (
            <Link key={p.id} to={`/product/${p.id}`} className="product-card">
              <img src={p.img} alt={p.name} />
              <p className="product-name">{p.name}</p>
              <p className="product-price">{p.price.toLocaleString()}원</p>
            </Link>
          ))}
        </div>
      </section>
    </>
  );
}

export default App;
