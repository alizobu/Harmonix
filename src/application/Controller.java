package application;
import javafx.scene.paint.Color;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import java.awt.FontMetrics;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;	
public class Controller{
  @FXML private Pane eklebtn; @FXML private Pane sesbtn; @FXML private Pane bytbtn; @FXML private Pane geribtn; @FXML private Pane ileribtn; @FXML private Pane baslatbtn; 
  @FXML private Polygon baslatrec; @FXML private Circle aracrc; @FXML private Line araline; @FXML private Circle baslatcrc; @FXML private Polygon ilerirec; @FXML private Label eklelbl;
  @FXML private Polygon gerirec; @FXML private Line ileriline; @FXML private Line geriline; @FXML private Polygon soundrec; @FXML private Line soundline1; @FXML private Circle eklecrc;
  @FXML private Line soundline2; @FXML private Line buyutline1; @FXML private Line buyutline2; @FXML private Polygon buyutrec1; @FXML private Polygon buyutrec2;@FXML private Label logo;
  @FXML private Label sarkitxt; @FXML private Label sanatcitxt; @FXML private ImageView imgbox; private MediaPlayer mediaPlayer; int x=0;int y=0;@FXML private Circle durdurcrc;@FXML private Line durdurline1;
  @FXML private Line durdurline2;@FXML private Slider mzkslider;@FXML private Label aktifsurelbl;@FXML private Line seskapa1;@FXML private Line seskapa2;@FXML private Label tamsurelbl;
  @FXML private Slider sesslider;@FXML private Button girisbtn;@FXML private TextField kytkullaniciadi;@FXML private TextField kytsifre;@FXML private Button kayitbtn;@FXML private TextField sifregrs;
  @FXML private TextField kullaniciadigrs;@FXML private Circle oturumcrc;@FXML private Circle oturumhead;@FXML private Ellipse oturumbody;@FXML private Button oturumkapabtn;@FXML private Label kullaniciadilbl;
  @FXML private Label sarkisayisilbl;@FXML private VBox musicListVBox;@FXML private TextField aratxt;@FXML private TextArea sonuclarTextArea;@FXML private Polygon solucgen;@FXML private Polygon sagucgen;
  @FXML private Line ustline;@FXML private Line altline;
  private List<String> songPaths = new ArrayList<>();
  private List<String> sanatciList = new ArrayList<>();
  private List<String> sarkiList = new ArrayList<>();
  private int currentIndex = 0;
  private int z = 0;
  private boolean isRepeatMode = false;
    @FXML
    public void labelClicked2() {
    	
        try {
        	Stage stage2 = (Stage) girisbtn.getScene().getWindow();
            stage2.close();
            Parent root = FXMLLoader.load(getClass().getResource("KayitOl.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btngrsclicked() {
        String kullaniciAdi = kullaniciadigrs.getText();
        String sifre = sifregrs.getText();
        String url = "jdbc:mysql://localhost:3306/mzkcalar";
        String user = "alipasa";
        String password = "alipasa";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM kullanicilar WHERE kullaniciadi = ? AND sifre = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, sifre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Giris Basarili");
                int kullaniciId = resultSet.getInt("id");
                String updateQuery = "UPDATE degeral SET degergir = ? WHERE id = 1";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setInt(1, kullaniciId);
                updateStatement.executeUpdate();
                String musicQuery = "SELECT sanatci, sarki, dosya_yolu FROM muzikler WHERE kullanici_id = ?";
                PreparedStatement musicStatement = connection.prepareStatement(musicQuery);
                musicStatement.setInt(1, kullaniciId);
                ResultSet musicResultSet = musicStatement.executeQuery();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HARMONIX.fxml"));
                Parent root = loader.load();
                Controller controller = loader.getController();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                int labelCount = 1;
                while (musicResultSet.next()) {
                    String sanatci = musicResultSet.getString("sanatci");
                    String sarki = musicResultSet.getString("sarki");
                    String dosyaYolu= musicResultSet.getString("dosya_yolu");
                    File dosya = new File(dosyaYolu);
                    String selectedfile11 = dosya.toURI().toString();
                    Label label = new Label(sanatci + " - " + sarki);
                    label.setTextFill(Color.WHITE);
                    label.setStyle("-fx-font-size: 16px;"); 
                    label.getStyleClass().add("label-style");
                    label.setId("label" + labelCount); 
                    label.setOnMouseClicked(event -> {
                        System.out.println(selectedfile11+sarki+sanatci);
                        //playMedia(selectedfile11, sarki, sanatci);
                    });
                    controller.getMusicListVBox().getChildren().add(label);
                }
                Stage stage2 = (Stage) girisbtn.getScene().getWindow();
                stage2.close();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı adı veya şifre hatalı!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Bağlantı hatası: " + e.getMessage());
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC sürücüsü bulunamadı.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("JDBC sürücüsü bulunamadı");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Bağlantı kapatma hatası: " + e.getMessage());
            }
        }
    }
    public VBox getMusicListVBox() {
        return musicListVBox;
    }
	public void mzkadd() throws IOException{
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("MP3 Dosyası Seç");
	    fileChooser.getExtensionFilters().addAll(
	            new FileChooser.ExtensionFilter("MP3 Dosyaları", "*.mp3"),
	            new FileChooser.ExtensionFilter("Tüm Dosyalar", "*.*")
	    );
	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	    	String selectedfile = selectedFile.toURI().toString();
	        Media media = new Media(selectedfile);
	        try {
	            String dosyaAdi = selectedFile.getName();
	            String dosyaYolu = selectedFile.getAbsolutePath();
	            String url = "jdbc:mysql://localhost:3306/mzkcalar";
	            String user = "alipasa";
	            String password = "alipasa";
	            Connection connection = null;
	            try {
	                connection = DriverManager.getConnection(url, user, password);
	                String selectQuery = "SELECT degergir FROM degeral WHERE id = 1";
	                PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
	                ResultSet resultSet = selectStatement.executeQuery();
	                int kullaniciId = 0;
	                if (resultSet.next()) {
	                    kullaniciId = resultSet.getInt("degergir");
	                }
	                String dosyaAdiUzantsiz = dosyaAdi.replaceFirst("[.][^.]+$", "");
	                String[] parcalar = dosyaAdiUzantsiz.split(" - ");
	                String ssarkiciAdi;
	                String ssarkiAdi;
	                if (parcalar.length >= 2) {
	                    ssarkiciAdi = parcalar[0];
	                    ssarkiAdi = parcalar[1];
	                } else {
	                    ssarkiciAdi = "Bilinmiyor";
	                    ssarkiAdi = "Bilinmiyor";
	                }
	                String checkQuery = "SELECT COUNT(*) FROM muzikler WHERE kullanici_id = ? AND sanatci = ? AND sarki = ?";
	                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
	                checkStatement.setInt(1, kullaniciId);
	                checkStatement.setString(2, ssarkiciAdi);
	                checkStatement.setString(3, ssarkiAdi);
	                ResultSet checkResultSet = checkStatement.executeQuery();
	                checkResultSet.next();
	                int count = checkResultSet.getInt(1);
	                if (count > 0) {
	                    System.out.println("Bu şarkı zaten mevcut. Veri eklenmedi.");
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Hata");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Bu şarkı zaten mevcut. Veri eklenmedi: ");
	                    alert.showAndWait();
	                } else {
	                    String insertQuery = "INSERT INTO muzikler (kullanici_id, sanatci, sarki, dosya_yolu) VALUES (?, ?, ?, ?)";
	                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
	                    preparedStatement.setInt(1, kullaniciId);
	                    preparedStatement.setString(2, ssarkiciAdi);
	                    preparedStatement.setString(3, ssarkiAdi);
	                    preparedStatement.setString(4, dosyaYolu);
	                    preparedStatement.executeUpdate();
	                    System.out.println("Müzik başarıyla eklendi!");
	                }
	            } catch (SQLException e) {
	                System.out.println("Veritabanına müzik eklenirken bir hata oluştu: " + e.getMessage());
	                Alert alert = new Alert(Alert.AlertType.ERROR);
	                alert.setTitle("Hata");
	                alert.setHeaderText(null);
	                alert.setContentText("Veritabanına müzik eklenirken bir hata oluştu: " + e.getMessage());
	                alert.showAndWait();
	            } finally {
	                if (connection != null) {
	                    connection.close();
	                }
	            }
	            System.out.println("Seçilen dosya: " + dosyaAdi);
	            String dosyaAdiUzantsiz = dosyaAdi.replaceFirst("[.][^.]+$", "");
                String[] parcalar = dosyaAdiUzantsiz.split(" - ");
                String ssarkiciAdi;
                String ssarkiAdi;
                if (parcalar.length >= 2) {
                    ssarkiciAdi = parcalar[0];
                    ssarkiAdi = parcalar[1];
                } else {
                    ssarkiciAdi = "Bilinmiyor";
                    ssarkiAdi = "Bilinmiyor";
                }
                String selectedfile1 = selectedFile.toURI().toString();
                playMedia(selectedfile1, ssarkiAdi, ssarkiciAdi);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
	private void playMedia(String selectedFile, String ssarkiAdi, String ssarkiciAdi) {
	    sarkitxt.setText(ssarkiAdi);
	    sanatcitxt.setText(ssarkiciAdi);
	    Media media1 = new Media(selectedFile);
	    mediaPlayer = new MediaPlayer(media1);
	    mediaPlayer.setOnReady(() -> {
	        Duration totalDuration = mediaPlayer.getTotalDuration();
	        double totalSeconds = totalDuration.toSeconds();
	        int minutes = (int) (totalSeconds / 60);
	        int seconds = (int) (totalSeconds % 60);
	        String formattedDuration = String.format("%02d:%02d", minutes, seconds); 
	        mzkslider.setMax(totalSeconds);
	        tamsurelbl.setText(formattedDuration);
	        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
	            double currentSeconds = newValue.toSeconds();
	            int currentMinutes = (int) (currentSeconds / 60);
	            int currentSecondsPart = (int) (currentSeconds % 60);
	            String formattedCurrentDuration = String.format("%02d:%02d", currentMinutes, currentSecondsPart);
	            aktifsurelbl.setText(formattedCurrentDuration); 
	        });
	    });
	}
    public void btnkytclicked() throws IOException {
        String kullaniciAdi = kytkullaniciadi.getText() ;
        String sifre = kytsifre.getText();
        
        String url = "jdbc:mysql://localhost:3306/mzkcalar";
        String user = "alipasa";
        String password = "alipasa";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            String insertQuery = "INSERT INTO kullanicilar (kullaniciadi, sifre) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, kullaniciAdi);
            preparedStatement.setString(2, sifre);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
            	Stage stage2 = (Stage) kayitbtn.getScene().getWindow();
                stage2.close();
            	Parent root = FXMLLoader.load(getClass().getResource("GirisYap.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                System.out.println("Kullanıcı başarıyla eklendi!");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı eklenirken bir hata oluştu!");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            System.out.println("Bağlantı hatası: " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Bağlantı hatası: " + e.getMessage());
            alert.showAndWait();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC sürücüsü bulunamadı.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("JDBC sürücüsü bulunamadı.");
            alert.showAndWait();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Bağlantı kapatma hatası: " + e.getMessage());
            }
        }
    }
    public void oturum() 
    {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("Oturum.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Controller controller = loader.getController();
            controller.initializeLabels();
            Stage stage2 = (Stage) eklebtn.getScene().getWindow();
            stage2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void initializeLabels() {
        String url = "jdbc:mysql://localhost:3306/mzkcalar";
        String user = "alipasa";
        String password = "alipasa";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String selectDegerQuery = "SELECT degergir FROM degeral WHERE id = 1";
            PreparedStatement degerStatement = connection.prepareStatement(selectDegerQuery);
            ResultSet degerResultSet = degerStatement.executeQuery();
            int deger = 0;
            if (degerResultSet.next()) {
                deger = degerResultSet.getInt("degergir");
            }
            degerResultSet.close();
            degerStatement.close();
            String selectUserQuery = "SELECT kullaniciadi FROM kullanicilar WHERE id = ?";
            PreparedStatement userStatement = connection.prepareStatement(selectUserQuery);
            userStatement.setInt(1, deger);
            ResultSet userResultSet = userStatement.executeQuery();
            if (userResultSet.next()) {
                String kullaniciAdi = userResultSet.getString("kullaniciadi");
                kullaniciadilbl.setText(kullaniciAdi);
            }
            userResultSet.close();
            userStatement.close();
            String selectSongCountQuery = "SELECT COUNT(*) AS songCount FROM muzikler WHERE kullanici_id = ?";
            PreparedStatement songCountStatement = connection.prepareStatement(selectSongCountQuery);
            songCountStatement.setInt(1, deger);
            ResultSet songCountResultSet = songCountStatement.executeQuery();
            int songCount = 0;
            if (songCountResultSet.next()) {
                songCount = songCountResultSet.getInt("songCount");
            }
            songCountResultSet.close();
            songCountStatement.close();
            sarkisayisilbl.setText(String.valueOf(songCount));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void oturumkapaclicked() throws IOException 
    {
    	Stage stage2 = (Stage) oturumkapabtn.getScene().getWindow();
        stage2.close();
    	Parent root1 = FXMLLoader.load(getClass().getResource("GirisYap.fxml"));
        Scene scene1 = new Scene(root1);
        Stage stage = new Stage();
        stage.setScene(scene1);
        stage.show();
    }
    public void oturumdevamclicked() throws IOException {
        Stage currentStage = (Stage) oturumkapabtn.getScene().getWindow();
        currentStage.close();
        String url = "jdbc:mysql://localhost:3306/mzkcalar";
        String user = "alipasa";
        String password = "alipasa";
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            String selectQuery = "SELECT degergir FROM degeral WHERE id = 1";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            int kullaniciId=0;
            if (resultSet.next()) {
                kullaniciId = resultSet.getInt("degergir");
            }
            String query = "SELECT sanatci, sarki, dosya_yolu FROM muzikler WHERE kullanici_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, kullaniciId);
            ResultSet musicResultSet = preparedStatement.executeQuery();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HARMONIX.fxml"));
            Parent root = loader.load();
            Controller controller = loader.getController();
            while (musicResultSet.next()) {
                String sanatci = musicResultSet.getString("sanatci");
                String sarki = musicResultSet.getString("sarki");
                String dosyaYolu = musicResultSet.getString("dosya_yolu");
                File dosya = new File(dosyaYolu);
                String selectedfile11 = dosya.toURI().toString();
                Label label = new Label(sanatci + " - " + sarki);
                label.setTextFill(Color.WHITE);
                label.setStyle("-fx-font-size: 16px;");
                label.getStyleClass().add("label-style");
                controller.getMusicListVBox().getChildren().add(label);
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void aratxtgrs() 
    {
    	aracrc.setStroke(Color.web("#F29F1B")); 
       araline.setStroke(Color.web("#F29F1B"));
    }
    public void aratxtcks() 
    {
    	aracrc.setStroke(Color.web("#8F8F8E")); 
        araline.setStroke(Color.web("#8F8F8E"));
    }
    public void initialize() {
    	if(aratxt!=null) {
    		aratxt.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    String arananKelime = newValue;
                    if(arananKelime!=null) {
                    	updateSearchResults(arananKelime);
                    }
                    
                }
            });
    	}
    }
    private void updateSearchResults(String arananKelime) {
        try {
            String url = "jdbc:mysql://localhost:3306/mzkcalar";
            String user = "alipasa";
            String password = "alipasa";
            Connection connection = DriverManager.getConnection(url, user, password);
            String selectQuery = "SELECT degergir FROM degeral WHERE id = 1";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            int kullaniciId = 0;
            if (resultSet.next()) {
                kullaniciId = resultSet.getInt("degergir");
            }
            String musicQuery = "SELECT sanatci, sarki, dosya_yolu FROM muzikler WHERE kullanici_id = ? AND sarki LIKE ?";
            PreparedStatement musicStatement = connection.prepareStatement(musicQuery);
            musicStatement.setInt(1, kullaniciId);
            musicStatement.setString(2, "%" + arananKelime + "%");
            ResultSet musicResultSet = musicStatement.executeQuery();
            sonuclarTextArea.clear();
            StringBuilder stringBuilder = new StringBuilder();
            while (musicResultSet.next()) {
                String sarki = musicResultSet.getString("sarki");
                stringBuilder.append(sarki).append("\n");
            }
            sonuclarTextArea.setText(stringBuilder.toString());
            if (!arananKelime.isEmpty()) {
                sonuclarTextArea.setVisible(true); 
            } else {
                sonuclarTextArea.setVisible(false); 
            }
            Text metin = new Text(sonuclarTextArea.getText());
            metin.setFont(sonuclarTextArea.getFont());
            metin.setWrappingWidth(sonuclarTextArea.getWidth());
            int satirSayisi = (int) (metin.getLayoutBounds().getHeight() / metin.getFont().getSize());
            double satirYuksekligi = metin.getFont().getSize();
            double yukseklik = Math.max(sonuclarTextArea.getMinHeight(), satirSayisi * satirYuksekligi);
            sonuclarTextArea.setPrefHeight(yukseklik);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addgrs() 
    {
        eklecrc.setStroke(Color.web("#F29F1B")); 
        eklecrc.setStrokeWidth(4.5);
        eklelbl.setTextFill(Color.web("#F29F1B"));
    }
    public void addcks()
    {
        eklecrc.setStroke(Color.web("#8F8F8E")); 
        eklecrc.setStrokeWidth(3);
        eklelbl.setTextFill(Color.web("#8F8F8E"));
    }
    public void oturumgrs() 
    {
        oturumcrc.setStroke(Color.web("#F29F1B")); 
        oturumcrc.setStrokeWidth(4);
        oturumhead.setFill(Color.web("#F29F1B"));
        oturumbody.setFill(Color.web("#F29F1B"));
    }
    public void oturumcks()
    {
        oturumcrc.setStroke(Color.web("#8F8F8E")); 
        oturumcrc.setStrokeWidth(3);
        oturumhead.setFill(Color.web("#8F8F8E"));
        oturumbody.setFill(Color.web("#8F8F8E"));
    }
    public void sesgrs() 
    {
    	soundrec.setStroke(Color.WHITE);
    	soundline1.setStroke(Color.WHITE);
    	soundline2.setStroke(Color.WHITE);
    	seskapa1.setStroke(Color.WHITE);
    	seskapa2.setStroke(Color.WHITE);
    }
    public void sescks() 
    {
    	soundrec.setStroke(Color.web("#8F8F8E"));
    	soundline1.setStroke(Color.web("#8F8F8E"));
    	soundline2.setStroke(Color.web("#8F8F8E"));
    	seskapa1.setStroke(Color.web("#8F8F8E"));
    	seskapa2.setStroke(Color.web("#8F8F8E"));
    }
    public void gerigrs() 
    {
    	gerirec.setFill(Color.WHITE); 
        geriline.setStroke(Color.WHITE);
    }
    public void gericks() 
    {
    	gerirec.setFill(Color.web("#8F8F8E")); 
        geriline.setStroke(Color.web("#8F8F8E"));
    }
    public void initialize2() {
        if (mediaPlayer != null) {
            mediaPlayer.setOnEndOfMedia(() -> {
                if (isRepeatMode) {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        }
    }
    public void tekrarclicked() {
        isRepeatMode = !isRepeatMode; 
        
        if (isRepeatMode) {
            sagucgen.setFill(Color.web("#F29F1B"));
            solucgen.setFill(Color.web("#F29F1B"));
            ustline.setStroke(Color.web("#F29F1B"));
            altline.setStroke(Color.web("#F29F1B"));
        } else {
            sagucgen.setFill(Color.web("#8F8F8E"));
            solucgen.setFill(Color.web("#8F8F8E"));
            ustline.setStroke(Color.web("#8F8F8E"));
            altline.setStroke(Color.web("#8F8F8E"));
        }
    }
    public void loadSongs() {
        String url = "jdbc:mysql://localhost:3306/mzkcalar";
        String user = "alipasa";
        String password = "alipasa";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
            String selectQuery = "SELECT degergir FROM degeral WHERE id = 1";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            ResultSet resultSet = selectStatement.executeQuery();
            int kullaniciId = 0;
            if (resultSet.next()) {
                kullaniciId = resultSet.getInt("degergir");
            }
            String musicQuery = "SELECT sanatci, sarki, dosya_yolu FROM muzikler WHERE kullanici_id = ?";
            PreparedStatement musicStatement = connection.prepareStatement(musicQuery);
            musicStatement.setInt(1, kullaniciId);
            ResultSet musicResultSet = musicStatement.executeQuery();
            songPaths.clear();
            sanatciList.clear();
            sarkiList.clear();
            while (musicResultSet.next()) {
                String dosyaYolu = musicResultSet.getString("dosya_yolu");
                songPaths.add(new File(dosyaYolu).toURI().toString());
                sanatciList.add(musicResultSet.getString("sanatci"));
                sarkiList.add(musicResultSet.getString("sarki"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void playSong(int index) {
        if (!songPaths.isEmpty()) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            String selectedFile = songPaths.get(index);
            Media media = new Media(selectedFile);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setOnReady(() -> {
                Duration totalDuration = mediaPlayer.getTotalDuration();
                double totalSeconds = totalDuration.toSeconds();
                int minutes = (int) (totalSeconds / 60);
                int seconds = (int) (totalSeconds % 60);
                String formattedDuration = String.format("%02d:%02d", minutes, seconds);
                tamsurelbl.setText(formattedDuration);
                mzkslider.setMax(totalSeconds);
                mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                    double currentSeconds = newValue.toSeconds();
                    int currentMinutes = (int) (currentSeconds / 60);
                    int currentSecondsPart = (int) (currentSeconds % 60);
                    String formattedCurrentDuration = String.format("%02d:%02d", currentMinutes, currentSecondsPart);
                    aktifsurelbl.setText(formattedCurrentDuration); 
                    mzkslider.setValue(currentSeconds);
                });
            });

            mediaPlayer.setOnEndOfMedia(() -> {
                ilericlicked(); // Sıradaki şarkıyı otomatik olarak oynatır
            });

            mediaPlayer.play();
            sarkitxt.setText(sarkiList.get(index));
            sanatcitxt.setText(sanatciList.get(index));
            updatePlayPauseButton(true);
        }
    }
    public void ilericlicked() {
        loadSongs();
        if (!songPaths.isEmpty()) {
            currentIndex++;
            if (currentIndex >= songPaths.size()) {
                currentIndex = 0;
            }
            playSong(currentIndex);
        }
    }
    public void gericlicked() {
        loadSongs();
        if (!songPaths.isEmpty()) {
            currentIndex--;
            if (currentIndex < 0) {
                currentIndex = songPaths.size() - 1;
            }
            playSong(currentIndex);
        }
    }
    public void ilerigrs() 
    {
    	ilerirec.setFill(Color.WHITE); 
        ileriline.setStroke(Color.WHITE);
    }
    public void ilericks() 
    {
    	ilerirec.setFill(Color.web("#8F8F8E")); 
        ileriline.setStroke(Color.web("#8F8F8E"));
    }
    public void baslatgrs() 
    {
    	baslatcrc.setStrokeWidth(1);
    	durdurcrc.setStrokeWidth(1);
    }
    public void baslatcks() 
    {
    	baslatcrc.setStrokeWidth(0);
    	durdurcrc.setStrokeWidth(1);
    }
    public void logogrs() 
    {
    	logo.setFont(new Font("Jersey 10 Regular", 44));
    }
    public void logocks() 
    {
    	logo.setFont(new Font("Jersey 10 Regular", 42));
    }
    @FXML
    public void sliderpressed(){
        mediaPlayer.seek(Duration.seconds(mzkslider.getValue()));
    }
    @FXML
    public void sespressed() 
    {
    	double yeniSesSeviyesi = sesslider.getValue() / 100; 
        mediaPlayer.setVolume(yeniSesSeviyesi);
    }
    public void baslat() {
        if (z % 2 == 0) {
            mediaPlayer.play();
            updatePlayPauseButton(true);
        } else {
            mediaPlayer.pause();
            updatePlayPauseButton(false);
        }
        z++;
    }
    private void updatePlayPauseButton(boolean isPlaying) {
        if (isPlaying) {
            durdurcrc.setVisible(true);
            durdurline1.setVisible(true);
            durdurline2.setVisible(true);
        } else {
            durdurcrc.setVisible(false);
            durdurline1.setVisible(false);
            durdurline2.setVisible(false);
        }
    }
    public void seskapa() 
    {
    	if(y%2==0) 
    	{
    		mediaPlayer.setVolume(0);
    		seskapa1.setVisible(true);seskapa2.setVisible(true);soundline1.setVisible(false);soundline2.setVisible(false);
    		y++;
    	}
    	else 
    	{
        	mediaPlayer.setVolume(1);
        	seskapa1.setVisible(false);seskapa2.setVisible(false);soundline1.setVisible(true);soundline2.setVisible(true);
    		y++;
    	}
    }
 }