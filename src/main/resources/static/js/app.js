const graphqlEndpoint = '/graphql';

async function graphqlQuery(query, variables = {}) {
    const response = await fetch(graphqlEndpoint, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ query, variables })
    });
    const result = await response.json();
    if (result.errors) {
        console.error('GraphQL Errors:', result.errors);
        throw new Error('GraphQL query failed');
    }
    return result.data;
}

async function fetchProductsByPrice() {
    const query = `
        query {
            allProductsByPriceAsc {
                id
                title
                price
                quantity
                desc
                user { fullname }
                categories { name }
            }
        }
    `;
    try {
        const data = await graphqlQuery(query);
        const products = data.allProductsByPriceAsc;
        const tbody = document.getElementById('productTableBody');
        tbody.innerHTML = '';
        products.forEach(product => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${product.id}</td>
                <td>${product.title}</td>
                <td>$${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.desc || ''}</td>
                <td>${product.user.fullname}</td>
                <td>${product.categories.map(c => c.name).join(', ')}</td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        alert('Error fetching products: ' + error.message);
    }
}

async function fetchProductsByCategory() {
    const categoryId = document.getElementById('categoryIdInput').value;
    if (!categoryId) {
        alert('Please enter a category ID');
        return;
    }
    const query = `
        query($categoryId: ID!) {
            productsByCategoryId(categoryId: $categoryId) {
                id
                title
                price
                quantity
                desc
                user { fullname }
            }
        }
    `;
    try {
        const data = await graphqlQuery(query, { categoryId });
        const products = data.productsByCategoryId;
        const tbody = document.getElementById('categoryProductTableBody');
        tbody.innerHTML = '';
        products.forEach(product => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${product.id}</td>
                <td>${product.title}</td>
                <td>$${product.price}</td>
                <td>${product.quantity}</td>
                <td>${product.desc || ''}</td>
                <td>${product.user.fullname}</td>
            `;
            tbody.appendChild(row);
        });
    } catch (error) {
        alert('Error fetching products by category: ' + error.message);
    }
}

document.getElementById('createUserForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fullname = document.getElementById('userFullname').value;
    const email = document.getElementById('userEmail').value;
    const password = document.getElementById('userPassword').value;
    const phone = document.getElementById('userPhone').value;
    const query = `
        mutation($fullname: String!, $email: String!, $password: String!, $phone: String) {
            createUser(fullname: $fullname, email: $email, password: $password, phone: $phone) {
                id
                fullname
                email
            }
        }
    `;
    try {
        const data = await graphqlQuery(query, { fullname, email, password, phone });
        alert('User created: ' + data.createUser.fullname);
        e.target.reset();
    } catch (error) {
        alert('Error creating user: ' + error.message);
    }
});

document.getElementById('createProductForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const title = document.getElementById('productTitle').value;
    const quantity = parseInt(document.getElementById('productQuantity').value);
    const desc = document.getElementById('productDesc').value;
    const price = parseFloat(document.getElementById('productPrice').value);
    const userId = parseInt(document.getElementById('productUserId').value);
    const categoryIds = document.getElementById('productCategoryIds').value.split(',').map(id => parseInt(id.trim())).filter(id => !isNaN(id));
    const query = `
        mutation($title: String!, $quantity: Int!, $desc: String, $price: Float!, $userId: ID!, $categoryIds: [ID!]) {
            createProduct(title: $title, quantity: $quantity, desc: $desc, price: $price, userId: $userId, categoryIds: $categoryIds) {
                id
                title
            }
        }
    `;
    try {
        const data = await graphqlQuery(query, { title, quantity, desc, price, userId, categoryIds });
        alert('Product created: ' + data.createProduct.title);
        e.target.reset();
    } catch (error) {
        alert('Error creating product: ' + error.message);
    }
});

document.getElementById('createCategoryForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('categoryName').value;
    const images = document.getElementById('categoryImages').value;
    const query = `
        mutation($name: String!, $images: String) {
            createCategory(name: $name, images: $images) {
                id
                name
            }
        }
    `;
    try {
        const data = await graphqlQuery(query, { name, images });
        alert('Category created: ' + data.createCategory.name);
        e.target.reset();
    } catch (error) {
        alert('Error creating category: ' + error.message);
    }
});