/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gyvatele;

/**
 *
 * @author buged
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.DelayQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.*;

public class Board extends JPanel implements ActionListener {

    private final int B_WIDTH = 900;
    private final int B_HEIGHT = 900;
    private final int DOT_SIZE = 30;
    private final int ALL_DOTS = 900;
    private final int RAND_POS = 29;
    private final int WALL_NR = 50;
    
    private int DELAY = 140;

    private int x[] = new int[ALL_DOTS];
    private int y[] = new int[ALL_DOTS];
    private final int clone[] = x;
    private final int allWallsX[] = new int [WALL_NR];
    private final int allWallsY[] = new int [WALL_NR];

    private int dots;
    private int apple_x;
    private int apple_y;
    private int scored = 0;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean inGame = true;

    private Timer timer;
    private Image ball;
    private Image apple;
    private Image head;
    private Image wall;
    
    JButton b1;
    JTextField t1;
    JFrame f;
    
    public Board() {
        
        initBoard();
    }
    
    private void initBoard() {

        addKeyListener(new TAdapter());
        
        setFocusable(true);

        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        loadImages();
        initGame();
    }

    private void loadImages() {

        ImageIcon iid = new ImageIcon("src/resources/GyvKunas.png");
        ball = iid.getImage();

        ImageIcon iia = new ImageIcon("src/resources/Maistas.png");
        apple = iia.getImage();

        ImageIcon iih = new ImageIcon("src/resources/GyvGalvaDesinen.png");
        head = iih.getImage();
        
        ImageIcon iiw = new ImageIcon("src/resources/Flower.png");
        wall = iiw.getImage();
    }

    private void initGame() {

        dots = 3;
        scored = 0;
        

        for (int z = 0; z < dots; z++) {
            x[z] = 60 - z * 30;
            y[z] = 60;
        }
        walls();
        locateApple();

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon iid = new ImageIcon("src/resources/FonasZole.png");
        g.drawImage(iid.getImage(), 0, 0, null);
        for(int i = 0; i < WALL_NR; i++){
            g.drawImage(wall, allWallsX[i], allWallsY[i], this);
        }
        scores(g);
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        
        if (inGame) {

            g.drawImage(apple, apple_x, apple_y, this);

            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                } else {
                    g.drawImage(ball, x[z], y[z], this);
                }
            }

            Toolkit.getDefaultToolkit().sync();

        } else {

            gameOver(g);
        }        
    }

    private void gameOver(Graphics g) {
        
        String heading = "Scoreboard:";
        Font big = new Font("Helvetica", Font.BOLD, 65);
        g.setColor(Color.white);
        g.setFont(big);
        
        
        ImageIcon iid = new ImageIcon("src/resources/FonasZole.png");
        g.drawImage(iid.getImage(), 0, 0, null);
        g.drawString(heading, 250, 180);
        
        Font small = new Font("Helvetica", Font.BOLD, 25);
        
        g.setFont(small);
        
        String[][] results = imtiScoreboard();

        for(int i = 0; i < results.length; i++){
            
            if(i > 4){
                break;
            }
            String res = String.format("%d. %-30s %-15s %30s", i+1, results[i][0], results[i][1], results[i][2]);
            g.drawString(res, 50, 300 + i*100);
            
            
        }
        
        
    }
    
    private String[][] imtiScoreboard(){
        
        String[] duom;
        DoubleLinkedList<String> list = new DoubleLinkedList<String>();
        File file = new File("src/scoreboard_data/Scoreboard.txt"); 
        try {
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine()) {
                
                list.DetiDuomenisA(sc.nextLine());
                
            }
        } 
        catch (FileNotFoundException ex) {
            
        }
        duom = new String[list.Dydis()];
        
        int z = 0;
        for(list.Pradzia(); list.Yra(); list.Kitas()){
            duom[z] = list.ImtiDuomenis();
            z++;
        }
        
        String[][] fake = new String[duom.length][3];
        for(int i = 0; i < duom.length; i++){
            String[] line = duom[i].split(",");
            fake[i][0] = line[0];
            fake[i][1] = line[1];
            fake[i][2] = line[2];
        }
        
        
        for(int i = 0; i < duom.length; i++){
            for(int j = i; j < duom.length; j++){
                if(Integer.parseInt(fake[i][1]) < Integer.parseInt(fake[j][1])){
                    String keisti = fake[i][0];
                    fake[i][0] = fake[j][0];
                    fake[j][0] = keisti;
                    keisti = fake[i][1];
                    fake[i][1] = fake[j][1];
                    fake[j][1] = keisti;
                    keisti = fake[i][2];
                    fake[i][2] = fake[j][2];
                    fake[j][2] = keisti;
                }
            }
        }
        
        
       
        
//        for(int i = 0; i < duom.length; i++){
//            duom[i] = fake[i][0] + fake[i][1] + fake[i][2];
//        }
        
        
        return fake;
    }

    private void checkApple() {

        if ((x[0] == apple_x) && (y[0] == apple_y)) {

            dots++;
            if(DELAY > 30){
            timer.stop();
            DELAY = DELAY - 5;
            timer = new Timer(DELAY, this);
            timer.start();
            }
            scored = scored + 5;
            locateApple();
            
        }
    }

    private void move() {

        for (int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }

        if (leftDirection) {
            x[0] -= DOT_SIZE;
        }

        if (rightDirection) {
            x[0] += DOT_SIZE;
        }

        if (upDirection) {
            y[0] -= DOT_SIZE;
        }

        if (downDirection) {
            y[0] += DOT_SIZE;
        }
    }
    
    

    private void checkCollision() {

        for (int z = dots; z > 0; z--) {

            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame = false;
            }
        }

        if (y[0] >= B_HEIGHT) {
            //inGame = false;
            y[0] = 0; 
        }

        if (y[0] < 0) {
            //inGame = false;
            y[0] = B_HEIGHT-30;
        }

        if (x[0] >= B_WIDTH) {
            //inGame = false;
            x[0] = 0;
        }

        if (x[0] < 0) {
            //inGame = false;
            x[0] = B_WIDTH -30;
        }
        
        for(int i = 0; i < WALL_NR; i++){
            if((x[0] == allWallsX[i]) && (y[0] == allWallsY[i])){
                inGame = false;
            }
        }
        
        if (!inGame) {
            timer.stop();
            textbox();
        }
    }
    
    
    private void walls(){
        
        // trecia eil nuo virsaus, pirmi 7 nuo kaires
        Random r = new Random();
        
        for(int i = 0; i < WALL_NR; i++){
            int h = (DOT_SIZE * r.nextInt(30));
            int w = (DOT_SIZE * r.nextInt(30));
            
            while (h <= 120 && w <= 210){
                h = (DOT_SIZE * r.nextInt(30));
                w = (DOT_SIZE * r.nextInt(30));
            }
            
            while (i % 10 <= 4){
                allWallsX[i] = w;
                allWallsY[i] = h;
                if(h > 450){
                    h=h-30;
                }
                else{
                    h=h+30;
                }
                
                i++;
            }
            
            h = (DOT_SIZE * r.nextInt(30));
            w = (DOT_SIZE * r.nextInt(30));
            
            while (h <= 120 && w <= 210){
                h = (DOT_SIZE * r.nextInt(30));
                w = (DOT_SIZE * r.nextInt(30));
            }
            
            while((i % 10 <= 9) && (i % 10 > 4)){
                allWallsX[i] = w;
                allWallsY[i] = h;
                if(w > 450){
                    w=w-30;
                }
                else{
                    w=w+30;
                }
                
                i++;
            }
            
            
        }
        
    }
    
    private void textbox(){
    
    f = new JFrame("Enter score to Scoreboard!");  
      
    JLabel txt = new JLabel("Score: " + Integer.toString(scored) + "!");
    txt.setBounds(100, 50, 300, 90);
    txt.setFont(new Font("Helvetica", Font.BOLD, 45));
    
    t1=new JTextField("Name/Nickname");  
    t1.setBounds(90,150, 200,30);  
    
    b1=new JButton("Submit!");
    b1.setBounds(150, 200, 90, 30);
    b1.addActionListener(this); 
    
    
    f.add(t1); f.add(b1); f.add(txt);
    
    f.setSize(400,400);  
    f.setLayout(null);  
    f.setVisible(true); 
    }
    
    private void scores(Graphics g){
        
        String msg = "Score: " + Integer.toString(scored);
        Font small = new Font("Helvetica", Font.BOLD, 45);
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, 10, 890);
        
    }

    private void locateApple() {

        int r = (int) (Math.random() * RAND_POS);
        apple_x = ((r * DOT_SIZE));

        r = (int) (Math.random() * RAND_POS);
        apple_y = ((r * DOT_SIZE));
        
        for(int i = 0; i < WALL_NR; i++){
            if(apple_x == allWallsX[i] && apple_y == allWallsY[i]){
                locateApple();
            }
        }
    }
    
    private void insertScore(String name){
        try(FileWriter fw = new FileWriter("src/scoreboard_data/Scoreboard.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw))
    {
        out.println(name + "," + Integer.toString(scored) + "," + java.time.LocalDate.now());
        //more code

    } 
        catch (IOException e) {
        //exception handling left as an exercise for the reader
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        
        if(e.getSource() == b1){
            insertScore(t1.getText());
            //repaint();
            f.dispose();
        }
        
        if (inGame) {

            checkApple();
            checkCollision();
            move();
        }

        repaint();
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
                
                ImageIcon iih = new ImageIcon("src/resources/GyvGalvaKairen.png");
                head = iih.getImage();
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
                
                ImageIcon iih = new ImageIcon("src/resources/GyvGalvaDesinen.png");
                head = iih.getImage();
            }

            if ((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
                
                ImageIcon iih = new ImageIcon("src/resources/GyvGalva.png");
                head = iih.getImage();
            }

            if ((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
                
                ImageIcon iih = new ImageIcon("src/resources/GyvGalvaZemyn.png");
                head = iih.getImage();
            }
            
            
            
        }
    }
}
