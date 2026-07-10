export default function ProductCard({
    product,
    addToCart
}) {

    return (

        <div className="col-md-4 mb-4">

            <div className="card shadow">

                <div className="card-body">

                    <img
                        src={product.imageUrl}
                        alt={product.productName}
                        width="150"
                        height="150"
                    />

                    <h5>
                        {product.productName}
                    </h5>

                    <p>

                        Brand :
                        {product.brand}

                    </p>

                    <p>

                        Price :
                        ₹ {product.price}

                    </p>

                    <p>

                        GST :
                        {product.gstPercentage} %

                    </p>

                    <button
                        className="btn btn-success"
                        onClick={() =>
                            addToCart(product)
                        }
                    >

                        Add To Cart

                    </button>

                </div>

            </div>

        </div>
    );
}