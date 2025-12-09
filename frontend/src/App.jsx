import React, { useContext } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { AuthContext, AuthProvider } from './AuthContext';
import './App.css';


import MainPage from './MainPage';
import LoginPage from './LoginPage';
import SellerPage from './SellerPage';
import ProductDetail from './ProductDetail';
import CartPage from './CartPage';
import SignupPage from './SignupPage';
import StorePage from './StorePage';

function App() {
    return (
        <AuthProvider>
            <Router>
                <AppContent />
            </Router>
        </AuthProvider>
    );
}

function AppContent() {
    const { user, logout } = useContext(AuthContext);

    return (
        <div className="app">
            <header className="header">
                <Link to="/" className="logo" style={{textDecoration:'none', color:'#03c75a', fontWeight:'bold', fontSize:'24px'}}>
                    N PlusStore
                </Link>
                <nav className="nav">
                    {user && user.role === 'SELLER' && (
                        <Link to="/seller" style={{marginRight:'20px'}}>판매자센터</Link>
                    )}
                    <Link to="/cart">장바구니</Link>

                    {user ? (
                        <span style={{marginLeft:'20px'}}>
                            <b style={{marginRight:'10px'}}>{user.name}님</b>
                            <button onClick={logout} style={{border:'1px solid #ddd', background:'white', cursor:'pointer', borderRadius:'4px', padding:'5px 10px'}}>로그아웃</button>
                        </span>
                    ) : (
                        <Link to="/login" style={{marginLeft:'20px'}}>로그인</Link>
                    )}
                </nav>
            </header>

            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/seller" element={<SellerPage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/signup" element={<SignupPage />} />
                <Route path="/product/:id" element={<ProductDetail />} />
                <Route path="/store/:sellerId" element={<StorePage />} />
                <Route path="/cart" element={<CartPage />} />
            </Routes>

            <footer className="footer" style={{textAlign:'center', padding:'30px', marginTop:'50px', borderTop:'1px solid #eee', color:'#888'}}>
                © NAVER Plus Store Clone.
            </footer>
        </div>
    );
}

export default App;