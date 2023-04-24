(() => console.log("It works!"))();

fetch("/api/products")
    .then(response => response.json())
    .then(data => console.log(data));
