package badrbillingsystem.controller;

import badrbillingsystem.models.CompanyInfo;
import badrbillingsystem.repos.companyinfo.CompanyInfoRepo;
import badrbillingsystem.utils.AlertMaker;
import badrbillingsystem.utils.ImageResizer;
import badrbillingsystem.utils.NotificationMaker;
import badrbillingsystem.utils.Constants;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
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
            String fullFilePath = txtLogo.getText();
            int index = fullFilePath.lastIndexOf(".");
            String extention = fullFilePath.substring(index + 1);
            System.out.println(extention);
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
            
            // copy and resize logo and qrcode
            File originalLogo = new File(txtLogo.getText());
            int index = txtLogo.getText().lastIndexOf(".");
            String logoExtention = txtLogo.getText().substring(index + 1);
            System.out.println(logoExtention);
            
            String logoOutPath = "out/logo/" + UUID.randomUUID() + "."+ logoExtention;
            File resizedLogo = new File(logoOutPath);
            
            ImageResizer.resizeImage(originalLogo, resizedLogo, logoExtention, Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT);
            i.setLogo(logoOutPath);
            i.setName(txtName.getText());
            i.setPhone(txtPhone.getText());
            
            int index2 = txtQRCode.getText().lastIndexOf(".");
            String qrCodeExtention = txtQRCode.getText().substring(index2 + 1);
            System.out.println(qrCodeExtention);
            
            File originalQrCode = new File(txtQRCode.getText());
            String qrCodeOutPath =  "out/logo/" + UUID.randomUUID() + "." + qrCodeExtention;
            File resizedQrCode = new File(qrCodeOutPath);
            
            System.out.println(logoOutPath);
            System.out.println(qrCodeOutPath);
            
            ImageResizer.resizeImage(originalQrCode, resizedQrCode, qrCodeExtention, Constants.LOGO_WIDTH, Constants.LOGO_HEIGHT);
            i.setQrCode(qrCodeOutPath);
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
//            if(info.getLogo().isEmpty()){
            txtLogo.setText(info.getLogo());
            txtQRCode.setText(info.getQrCode());
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
