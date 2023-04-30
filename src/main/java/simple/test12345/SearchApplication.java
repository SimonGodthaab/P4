package simple.test12345;

public class SearchApplication {


    String carBrand, carModel;
    Double Price;

    public SearchApplication(String carBrand, String carModel, Double price) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.Price = price;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public Double getPrice() {
        return Price;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setPrice(Double price) {
        Price = price;
    }


}

