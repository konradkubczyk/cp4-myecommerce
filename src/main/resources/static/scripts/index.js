const orderNowButton = document.querySelector('.orderNow');
const checkoutLayerEl = document.querySelector('#checkout');
const checkoutForm = document.querySelector('#checkout form');

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

            if (offer.total && offer.productsCount > 0) {
                orderNowButton.removeAttribute('disabled');
            }
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

checkoutForm.addEventListener('submit', (e) => {
    e.preventDefault();

    const data = new FormData(checkoutForm);
    let request = {};
    for (let [key, value] of data) {
        request[key] = value;
    }

    fetch("/api/accept-offer", {
        method: 'POST',
        body: JSON.stringify(request),
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then(r => r.json())
        .then(data => window.location.href = data.paymentUrl);
});

(async () => {
    const productsList = document.querySelector('#productsList');

    refreshCurrentOffer();

    const products = await getProducts();

    products
        .map(p => createProductComponent(p))
        .map(el => initializeAddToCartHandler(el))
        .forEach(el => productsList.appendChild(el));
})();
