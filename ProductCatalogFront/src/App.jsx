import { useEffect, useState } from 'react'
import './App.css'
import ProductList from './ProductList';
import CategoryFilter from './CategoryFilter';

function App() {
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortOrder, setSortOrder] = useState("asc");

  useEffect(() => {
    fetch('http://localhost:8080/api/categories')
      .then(response => {
        if (!response.ok) throw new Error("Failed to fetch categories");
        return response.json();
      })
      .then(data => setCategories(data))
      .catch(err => {
        console.error(err);
        setCategories([]);
      });
  }, []);

  useEffect(() => {
    let url = '';

    if (searchTerm.trim()) {
      url = `http://localhost:8080/api/products/search?keyword=${encodeURIComponent(searchTerm)}&sort=${sortOrder}`;
    } else if (selectedCategory) {
      url = `http://localhost:8080/api/products/category/${selectedCategory}`;
    } else {
      url = `http://localhost:8080/api/products`;
    }

    fetch(url)
      .then(response => {
        if (!response.ok) throw new Error("Failed to fetch products");
        return response.json();
      })
      .then(data => setProducts(data))
      .catch(err => {
        console.error(err);
        setProducts([]);
      });
  }, [searchTerm, sortOrder, selectedCategory]);



  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSortChange = (event) => {
    setSortOrder(event.target.value);
  };

  const handleCategorySelect = (categoryId) => {
    setSelectedCategory(categoryId ? Number(categoryId) : null);
  };

  

  return (
    <div className='container'>
      <h1 className='my-4'>Product Catalog</h1>

      <div className='row align-items-center mb-4'>
        <div className='col-md-3 col-sm-12 mb-2'>
          <CategoryFilter categories={categories} onSelect={handleCategorySelect}/>
        </div>

        <div className='col-md-5 col-sm-12 mb-2'>
          <input
            type='text'
            className='form-control'
            placeholder='Search for products'
            onChange={handleSearchChange}
            />
        </div>

        <div className='col-md-4 col-sm-12 mb-2'>
          <select className='form-control' onChange={handleSortChange}>
            <option value="asc">Sort by Price: Low to High</option>
            <option value="desc">Sort by Price: High to Low</option>
          </select>
        </div>
      </div>

      <div>
        {products.length ? (
          // Display products
          <ProductList products={products}/>
        ) : (
          <p>No Products Found</p>
        )}
      </div>
    </div>
  )
}

export default App
