import carousel1 from "../images/e-commerce.jpg";

const Carousel = () => {
  return (
    <div
      id="carouselExampleCaptions"
      class="carousel slide"
      data-bs-ride="false"
    >
      <div class="carousel-indicators">
      </div>
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src={carousel1} class="d-block w-100" alt="..." />
        </div>
      </div>
    </div>
  );
};

export default Carousel;
