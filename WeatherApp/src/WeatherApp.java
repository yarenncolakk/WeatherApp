import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class WeatherApp extends JFrame {
    private JTextField txt;
    private JLabel lblCity, lblTemp, lblHum, lblImgWeather, lblImgHum;
    private JButton btn;

    WeatherApp(){
        try{
            myFile();
        }catch (Exception e){
            System.out.println("Error: " +e);
        }
        setSize(400,500);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        txt = new JTextField();
        txt.setBounds(10,10,320,40);
        txt.setFont(new Font("Dialog",Font.PLAIN,20));
        add(txt);

        btn = new JButton(loadImage("src/Img/search.png"));
        btn.setBounds(335,10,40,40);
        //btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(btn);

        lblImgWeather = new JLabel();
        lblImgWeather.setBounds(60,60,256,256);
        lblImgWeather.setIcon(loadImage("src/Img/cloudy.png"));
        add(lblImgWeather);

        lblCity = new JLabel("WeatherApp");
        lblCity.setBounds(140,310,180,30);
        lblCity.setFont(new Font("Dialog",Font.PLAIN,20));
        add(lblCity);

        lblTemp = new JLabel("22°C");
        lblTemp.setBounds(180,325,50,50);
        lblTemp.setFont(new Font("Dialog",Font.PLAIN,15));
        add(lblTemp);

        lblImgHum = new JLabel();
        lblImgHum.setBounds(10,395,50,50);
        lblImgHum.setIcon(loadImage("src/Img/humidity.png"));
        add(lblImgHum);

        lblHum = new JLabel("79%");
        lblHum.setBounds(50,395,50,50);
        setFont(new Font("Dialog",Font.PLAIN,15));
        add(lblHum);

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/myFile")));
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        String[] parts = line.split(",");
                        if (parts[0].equalsIgnoreCase(txt.getText())){
                            lblCity.setText(parts[0]);
                            lblTemp.setText(parts[1] + "°C");
                            lblHum.setText(parts[2] + "%");
                            int temp = Integer.parseInt(parts[1]);
                            if (temp < 5){
                                lblImgWeather.setIcon(loadImage("src/Img/cold.png"));
                            } else if (temp < 20){
                                lblImgWeather.setIcon(loadImage("src/Img/cloudy.png"));
                            }
                            else{
                                lblImgWeather.setIcon(loadImage("src/Img/sunny.png"));
                            }
                        }
                    }
                } catch (Exception exception){
                    System.out.print("Error: " +exception);
                }
            }
        });
        setVisible(true);
    }
    public ImageIcon loadImage(String path){
        try{
            BufferedImage bufferedImage = ImageIO.read(new File(path));
            return new ImageIcon(bufferedImage);
        }catch (Exception exception){
            System.out.print("Error: " +exception);
        }
        return null;
    }

    public void myFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("src/myFile")));
        StringBuilder str = new StringBuilder();
        str.append("İstanbul,30,79\n").append("Giresun,28,88\n").append("Ankara,19,28\n").append("New York,4,12");
        bufferedWriter.append(str);
        bufferedWriter.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeatherApp::new);
    }
}

