module com.zenveus.the_culinary_academy {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires jakarta.persistence;
    requires static lombok;
    requires org.hibernate.orm.core;

    opens com.zenveus.the_culinary_academy to javafx.fxml;
    exports com.zenveus.the_culinary_academy;
    exports com.zenveus.the_culinary_academy.controllers;
    opens com.zenveus.the_culinary_academy.controllers to javafx.fxml;
}