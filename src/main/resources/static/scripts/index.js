const a = 5;
let b = 5;

const myFunction = (foo) => {
    console.log(foo);
}

const getProducts = () => {
    return fetch("/api/products")
        .then(response => response.json());
}

const addToCart = (productId) => {
    return fetch(`/api/cart/${productId}`, {
        method: "POST",
        body: JSON.stringify({})
    });
}

const createHtmlElementFromString = (template) => {
    let tmpElement = document.createElement('div');
    tmpElement.innerHTML = template.trim();

    return tmpElement.firstChild;
}

const createProductComponent = (product) => {
    const template = `
        <div class="product border rounded-3 d-flex flex-row justify-content-between align-items-center shadow-none p-3 gap-2">
            <!-- <img src="..." alt="..."> -->
            <div class="d-flex gap-3 align-items-baseline">
                <h5 class="card-title">${product.name}</h5>
                <p class="card-text text-secondary">${product.desc}</p>
            </div>
            <div>
            <button class="product__add-to-cart btn btn-primary h-100" style="width: 10rem;" data-product-id="${product.id}" href="#">
                Buy for ${product.price} PLN
            </button>
            </div>
        </div>
    `;

    return createHtmlElementFromString(template);
}

const getCurrentOffer = () => {
    return fetch("/api/current-offer")
        .then(response => response.json());
}
const refreshCurrentOffer = () => {
    const offerElement = document.querySelector('.cart');

    getCurrentOffer()
        .then(offer => {
            offerElement.querySelector('.total').textContent = `${offer.total}`;
            offerElement.querySelector('.productsCount').textContent = `${offer.productsCount || 0}`;
        });
}

const initializeAddToCartHandler = (el) => {
    const btn = el.querySelector('button.product__add-to-cart');
    btn.addEventListener('click', () => {
        addToCart(btn.getAttribute('data-product-id'))
            .then(refreshCurrentOffer())
    });

    return el;
}

(async () => {
    console.log("It works :)");
    const productsList = document.querySelector('#productsList');

    refreshCurrentOffer();

    const products = await getProducts();

    products
        .map(p => createProductComponent(p))
        .map(el => initializeAddToCartHandler(el))
        .forEach(el => productsList.appendChild(el));

    console.log("post get products");
})();
