import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

function StorePage() {
    const { sellerId } = useParams();
    const [products, setProducts] = useState([]);
    const [storeInfo, setStoreInfo] = useState({ storeName: "로딩중..." });
    const [loading, setLoading] = useState(true);

    // 🟢 [추가] 현재 선택된 카테고리 (기본값: '전체')
    const [selectedCategory, setSelectedCategory] = useState('전체');

    useEffect(() => {
        // 1. 스토어 정보
        fetch(`http://localhost:8080/api/sellers/${sellerId}`)
            .then(res => res.ok ? res.json() : { storeName: "알 수 없는 스토어" })
            .then(data => setStoreInfo(data));

        // 2. 상품 목록
        fetch(`http://localhost:8080/api/seller/products/${sellerId}`)
            .then(res => res.json())
            .then(data => {
                setProducts(data);
                setLoading(false);
            })
            .catch(() => setLoading(false));
    }, [sellerId]);

    // 🟢 [로직] 상품목록에서 '카테고리 이름'들만 뽑아서 중복 제거 (메뉴 만들기용)
    const categoryList = ['전체', ...new Set(products.map(p => p.category ? p.category.categoryName : '기타'))];

    // 🟢 [로직] 선택된 카테고리에 맞는 상품만 필터링
    const filteredProducts = selectedCategory === '전체'
        ? products
        : products.filter(p => (p.category ? p.category.categoryName : '기타') === selectedCategory);

    if (loading) return <div style={{textAlign:'center', padding:'100px'}}>로딩 중...</div>;

    return (
        <div style={{ maxWidth: '1200px', margin: '0 auto' }}>

            {/* 1. 스토어 헤더 (가운데 정렬 + 깔끔하게) */}
            <div style={{ textAlign:'center', padding: '40px 0 20px', borderBottom: '1px solid #eee' }}>
                <h1 style={{ fontSize: '36px', fontWeight:'bold', margin: '0 0 10px', color:'#333' }}>
                    {storeInfo.storeName}
                </h1>

            </div>

            {/* 2. 가로형 카테고리 메뉴 (네이버 스타일) */}
            <div style={{ position:'sticky', top:0, background:'white', zIndex:10, borderBottom:'1px solid #ddd', padding:'0 20px' }}>
                <div style={{ display: 'flex', gap: '30px', overflowX: 'auto', whiteSpace: 'nowrap', padding: '15px 0' }}>
                    {categoryList.map(cat => (
                        <span
                            key={cat}
                            onClick={() => setSelectedCategory(cat)}
                            style={{
                                cursor: 'pointer',
                                fontSize: '16px',
                                fontWeight: selectedCategory === cat ? 'bold' : 'normal',
                                color: selectedCategory === cat ? '#03c75a' : '#333', // 선택되면 초록색
                                borderBottom: selectedCategory === cat ? '3px solid #03c75a' : '3px solid transparent',
                                paddingBottom: '5px',
                                transition: 'all 0.2s'
                            }}
                        >
                            {cat}
                        </span>
                    ))}
                </div>
            </div>

            {/* 3. 상품 그리드 (필터링된 결과 표시) */}
            <div style={{ padding: '30px 20px', backgroundColor: '#f9f9f9', minHeight: '500px' }}>
                <div style={{ display: 'flex', justifyContent:'space-between', marginBottom:'15px', fontSize:'14px', color:'#666'}}>
                    <span>총 <b>{filteredProducts.length}</b>개</span>
                </div>

                {filteredProducts.length === 0 ? (
                    <div style={{ textAlign: 'center', padding: '100px', color: '#999' }}>
                        등록된 상품이 없습니다.
                    </div>
                ) : (
                    <div style={{
                        display: 'grid',
                        gridTemplateColumns: 'repeat(4, 1fr)', // 한 줄에 4개
                        gap: '20px'
                    }}>
                        {filteredProducts.map(p => (
                            <Link
                                key={p.productId}
                                to={`/product/${p.productId}`}
                                style={{
                                    textDecoration: 'none',
                                    color: 'black',
                                    background: 'white',
                                    borderRadius: '8px',
                                    overflow:'hidden',
                                    display:'block',
                                    border: '1px solid #eee',
                                    transition: 'transform 0.2s'
                                }}
                                onMouseOver={e => e.currentTarget.style.transform = 'translateY(-5px)'}
                                onMouseOut={e => e.currentTarget.style.transform = 'translateY(0)'}
                            >
                                {/* 이미지 영역 */}
                                <div style={{ height: '250px', background: '#f4f4f4', position:'relative' }}>
                                    {p.imageUrl ? (
                                        <img src={p.imageUrl} alt={p.name} style={{width:'100%', height:'100%', objectFit:'cover'}} />
                                    ) : (
                                        <div style={{display:'flex', alignItems:'center', justifyContent:'center', height:'100%', color:'#ccc'}}>No Image</div>
                                    )}
                                </div>

                                {/* 텍스트 정보 영역 */}
                                <div style={{ padding: '15px' }}>
                                    <div style={{fontSize:'16px', marginBottom:'8px', lineHeight:'1.4', height:'44px', overflow:'hidden', textOverflow:'ellipsis', display:'-webkit-box', WebkitLineClamp:2, WebkitBoxOrient:'vertical'}}>
                                        {p.name}
                                    </div>
                                    <div style={{marginBottom:'5px'}}>
                                        <span style={{color:'#03c75a', fontWeight:'bold', fontSize:'20px', marginRight:'5px'}}>
                                            {p.price.toLocaleString()}
                                        </span>
                                        <span style={{fontSize:'14px'}}>원</span>
                                    </div>
                                    <div style={{fontSize:'12px', color:'#999'}}>
                                        <span>구매 999+</span> · <span>리뷰 50+</span>
                                    </div>
                                </div>
                            </Link>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
}

export default StorePage;