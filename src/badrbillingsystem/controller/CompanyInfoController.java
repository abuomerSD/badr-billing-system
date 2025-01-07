package badrbillingsystem.controller;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.NotificationMaker;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;


public class CompanyInfoController implements Initializable{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTextFieldsData();
    }
    
    @FXML
    private TextField txtTaxNumber;

    @FXML
    private TextField txtBranch;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextArea txtInstructions;

    @FXML
    private TextField txtLogo;

    @FXML
    private TextField txtQRCode;
    
    @FXML
    private ImageView imgLogo;
    
    @FXML
    private ImageView imgQRCode;
    
    CompanyInfoRepo repo = new CompanyInfoRepo();

    @FXML
    void chooseLogo(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images files ", "*.png", "*.jpg", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(null);
            InputStream in = new FileInputStream(file);
            imgLogo.setImage(new Image(in));
            txtLogo.setText(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void chooseQRCode(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images files ", "*.png", "*.jpg", "*.jpeg");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(null);
            InputStream in = new FileInputStream(file);
            imgQRCode.setImage(new Image(in));
            txtQRCode.setText(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    @FXML
    void updateInfo(ActionEvent event) {
        try {
            CompanyInfo i = new CompanyInfo();
            i.setAddress(txtAddress.getText());
            i.setBranch(txtBranch.getText());
            i.setInstructions(txtInstructions.getText());
            i.setLogo(txtLogo.getText());
            i.setName(txtName.getText());
            i.setPhone(txtPhone.getText());
            i.setQrCode(txtQRCode.getText());
            i.setTaxNumber(txtTaxNumber.getText());
            
            Optional<ButtonType> r = AlertMaker.showConfirmationAlert("هل تريد تعديل بيانات المؤسسة؟");
            if(r.get() != ButtonType.OK)
                return;
            repo.update(i);
            NotificationMaker.showInformation("تم تعديل بيانات المؤسسة بنجاح");
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }

    }

    private void setTextFieldsData() {
        try {
            
            CompanyInfo info = repo.findById(1);
            txtAddress.setText(info.getAddress());
            txtBranch.setText(info.getBranch());
            txtInstructions.setText(info.getInstructions());
            txtName.setText(info.getName());
            txtPhone.setText(info.getPhone());
            txtTaxNumber.setText(info.getTaxNumber());
            txtLogo.setText("src/badrbillingsystem/resources/images/LOGO-removebg-preview.png");
            txtQRCode.setText("src/badrbillingsystem/resources/images/QR-removebg-preview.png");
            InputStream logoImage = new FileInputStream(new File(info.getLogo()));
            InputStream qrcodeImage = new FileInputStream(new File(info.getQrCode()));
            imgLogo.setImage(new Image(logoImage));
            imgQRCode.setImage(new Image(qrcodeImage));
        } catch (Exception e) {
            e.printStackTrace();
            AlertMaker.showErrorALert(e.toString());
        }
    }
    
}
