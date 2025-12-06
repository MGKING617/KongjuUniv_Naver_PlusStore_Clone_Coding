import React, { useEffect, useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';

// 페이지 컴포넌트 불러오기
import LoginPage from './LoginPage';
import SellerPage from './SellerPage';
import ProductDetail from './ProductDetail';
import CartPage from './CartPage';

function App() {
    return (
        <Router>
            <div className="app">
                <header className="header">
                    <div className="logo" style={{color:'#03c75a', fontWeight:'bold', fontSize:'24px'}}>N Pay</div>
                    <nav className="nav">
                        <Link to="/">메인</Link>
                        <Link to="/seller">판매자센터</Link>
                        <Link to="/cart">장바구니</Link>
                        <Link to="/login">로그인</Link>
                    </nav>
                </header>

                <Routes>
                    <Route path="/" element={<MainPage />} />
                    <Route path="/seller" element={<SellerPage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/product/:id" element={<ProductDetail />} />
                    <Route path="/cart" element={<CartPage />} />
                </Routes>

                <footer className="footer" style={{marginTop:'50px', padding:'20px', borderTop:'1px solid #eee', textAlign:'center', color:'#888'}}>
                    © NAVER Corp.
                </footer>
            </div>
        </Router>
    );
}

// [메인 페이지] 백엔드 DB에서 진짜 상품 가져오기
function MainPage() {
    // 상품 목록 상태 (TypeScript 문법 <any[]> 제거함)
    const [products, setProducts] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        // 백엔드 API 호출 (8080 포트)
        fetch("http://localhost:8080/api/products")
            .then(res => {
                if (!res.ok) throw new Error("네트워크 응답 실패");
                return res.json();
            })
            .then(data => {
                console.log("DB 상품 데이터:", data);
                setProducts(data);
            })
            .catch(err => {
                console.error("상품 조회 실패:", err);
                setError("서버가 꺼져있거나 연결할 수 없습니다.");
            });
    }, []);

    return (
        <>
            <section className="main-banner">
                <div className="banner-content">
                    <h2>네이버플러스 멤버십</h2>
                    <p>쇼핑 혜택을 한 곳에서!</p>
                    <button className="join-btn">가입하고 혜택받기</button>
                </div>
            </section>

            <section className="product-section">
                <h3>인기 상품</h3>

                {/* 에러 메시지 표시 */}
                {error && <div style={{color:'red', textAlign:'center', marginBottom:'20px'}}>⚠️ {error}</div>}

                {/* 상품 목록 표시 */}
                {products.length === 0 && !error ? (
                    <div style={{textAlign:'center', padding:'40px', color:'#999'}}>
                        등록된 상품이 없습니다.<br/>
                        <Link to="/seller" style={{color:'#03c75a', fontWeight:'bold'}}>판매자 센터</Link>에서 상품을 등록해보세요!
                    </div>
                ) : (
                    <div className="product-list">
                        {products.map(p => (
                            <Link key={p.id} to={`/product/${p.id}`} className="product-card">
                                {/* 이미지가 없으면 기본 이미지 */}
                                <img src={p.img || "https://via.placeholder.com/300"} alt={p.name} />
                                <p className="product-name">{p.name}</p>
                                <p className="product-price">{p.price ? p.price.toLocaleString() : 0}원</p>
                                <p style={{fontSize:'12px', color:'#999'}}>{p.sellerName}</p>
                            </Link>
                        ))}
                    </div>
                )}
            </section>
        </>
    );
}

export default App;