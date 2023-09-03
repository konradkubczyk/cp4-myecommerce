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

    let priceWithoutDiscount = 0;

    let orderSummaryHTML = `
        <table class="table bg-body-secondary mb-0">
            <thead>
                <tr>
                    <th scope="col" class="bg-body-secondary">Product</th>
                    <th scope="col" class="bg-body-secondary">Price</th>
                    <th scope="col" class="bg-body-secondary">Quantity</th>
                    <th scope="col" class="bg-body-secondary">Value</th>
                </tr>
            </thead>
            <tbody>
    `;

    getCurrentOffer()
        .then(offer => {
            offerElement.querySelector('.total').textContent = `${offer.total}`;
            offerElement.querySelector('.productsCount').textContent = `${offer.productsCount || 0}`;

            if (offer.total && offer.productsCount > 0) {
                orderNowButton.removeAttribute('disabled');
            }

            Object.values(offer.orderLines).forEach(orderLine => {
                orderSummaryHTML += `
                    <tr>
                        <td class="bg-body-secondary">${orderLine.name}</td>
                        <td class="bg-body-secondary">${orderLine.unitPrice}</td>
                        <td class="bg-body-secondary">${orderLine.quantity}</td>
                        <td class="bg-body-secondary">${orderLine.lineTotal}</td>
                    </tr>
                `;
                priceWithoutDiscount += orderLine.unitPrice * orderLine.quantity;
            });

            orderSummaryHTML += `
                </tbody>
                <tfoot>
                    <tr class="fw-bold">
                        <td colspan="3" class="bg-body-secondary text-secondary">Discount</td>
                        <td class="bg-body-secondary text-secondary">${(offer.total - priceWithoutDiscount).toFixed(2)}</td>
                    </tr>
                    <tr class="fw-bold">
                        <td colspan="3" class="bg-body-secondary border-bottom-0">Total</td>
                        <td class="bg-body-secondary border-bottom-0">${offer.total}</td>
                    </tr>
                </tfoot>
            </table>
            `;

            document.querySelector('.orderSummary').innerHTML = orderSummaryHTML;

            document.querySelector('.payButton').innerText = `Pay ${offer.total} PLN`;
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
