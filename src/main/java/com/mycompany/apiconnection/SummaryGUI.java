/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.apiconnection;

import com.mongodb.client.FindIterable;
import static com.mongodb.client.model.Projections.exclude;
import com.mycompany.apiconnection.ChatGUI;
import com.mycompany.apiconnection.FileManager;
import com.mycompany.apiconnection.LoginGUI;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import java.awt.event.ActionEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.bson.Document;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.json.JSONObject;

/**
 *
 * @author tombr
 */
public class SummaryGUI extends javax.swing.JFrame {

    private String API_key;
    private List<SyndEntryImpl> newsEntries;
    private int articleIndex = -1;
    private GUIManager guiManager;
    private APIManager apiManager;
    private MongoDBManager mongoManager;
    private UserSetting userSetting;
    private MongoDBManager leaderboardConnection;
    private ArrayList<Double> totalValues = new ArrayList<>();
    /**
     * Creates new form SummaryGUI
     */
    public SummaryGUI() {
        initComponents();
        leaderboardConnection = new MongoDBManager();
    }
    
    public void setUserSetting(UserSetting userSetting) {
        this.userSetting = userSetting;
    }
    
    public void setNewsEntries(List<SyndEntryImpl> list) {
        newsEntries = list;
    }
    
    public void setGUIManager(GUIManager guiManager) {
        this.guiManager = guiManager;
    }
    
    public void setAPIManager(APIManager apiManager) {
        this.apiManager = apiManager;
    }
    
    public void setMongoManager(MongoDBManager mongoManager) {
        this.mongoManager = mongoManager;
    }
    
    public MongoDBManager getLeaderboardConnection() {
        return leaderboardConnection;
    }
    
    public void loadNews() {
        summaryTA.setText("");
        for (int i = 0; i < newsEntries.size(); i++) {
            summaryTA.append("- " + newsEntries.get(i).getTitle() + "\n");
            summaryTA.append(newsEntries.get(i).getPublishedDate() + "\n\n");
        }
        summaryTA.getCaret().moveDot(0);
        leaderboardConnection.connect("adminUser", "upPGU?7fZ+5d@4k", "mycluster.eqvxj", "leaderboard_database", "leaderboard", false);
        if (leaderboardConnection.isConnected())
            leaderboardConnection.updateLeaderboard(((LoginGUI)guiManager.getFrame("loginFrame")).getUser());
    }
    
    public void drawChart() {
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        FindIterable<Document> output = mongoManager.getCollection().find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        // refresh values
        totalValues.clear();
        double price;
        if (apiManager.getObject() != null) {
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < apiManager.getJSONLength(); j++) {
                    if (apiManager.getObject().query(apiManager.getPointers().get(j).toString()).equals(results.get(i).getString("currency"))) {
                        price = Double.valueOf(apiManager.getObject().query(apiManager.getValues().get(j)).toString()) * Double.valueOf(results.get(i).getString("quantity"));
                        totalValues.add(price);
                    }
                }
            }
        }
        for (int i = 0; i < results.size(); i++) {
            myPieDataset.setValue(results.get(i).getString("currency"), BigDecimal.valueOf(totalValues.get(i)).setScale(2, RoundingMode.HALF_EVEN));
            }
        JFreeChart myChart = ChartFactory.createPieChart("My Crypto Portfolio", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Chart", myChart);
        // allow the user to save the chart as an image
        javax.swing.JButton saveBTN = new javax.swing.JButton();
        saveBTN.setText("Save");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStream out = new FileOutputStream("chart.png");
                    ChartUtilities.writeChartAsPNG(out, myChart, chartFrame.getWidth(), chartFrame.getHeight());
                    JOptionPane.showMessageDialog(null, "Image saved to .png file.");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        // add text area
        javax.swing.JTextArea infoTA = new javax.swing.JTextArea();
        chartFrame.add(saveBTN);
        chartFrame.add(infoTA);
        chartFrame.setLayout(null);
        saveBTN.setBounds(chartFrame.getX(), chartFrame.getHeight(), 75, 20);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
        // calculate sum of all currency values
        double portfolioValue = 0;
        for (int i = 0; i < totalValues.size(); i++) {
            portfolioValue += totalValues.get(i);
        }
        infoTA.setText("Total value of portfolio: €" + BigDecimal.valueOf(portfolioValue).setScale(2, RoundingMode.HALF_EVEN));
    }
    
    public void drawOfflineChart(String recordLine) {
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        // extract data from a single line
        ArrayList<String> coinNames = new ArrayList<>();
        ArrayList<Double> coinPrices = new ArrayList<>();
        // extract coin names
        for (int i = 0; i < recordLine.length() - 6; i++) {
            if (recordLine.charAt(i) == 'C' && recordLine.charAt(i+1) == 'o' && recordLine.charAt(i+2) == 'i'
                    && recordLine.charAt(i+3) == 'n' && recordLine.charAt(i+4) == ':'
                    && recordLine.charAt(i+5) == ' ') {
                int k = i+6;
                String feed = "";
                while (recordLine.charAt(k) != '|') {
                    feed += recordLine.charAt(k);
                    k++;
                }
                coinNames.add(feed);
                System.out.println("Adding " + feed);
            }
        }
        // extract coin prices
        for (int i = 0; i < recordLine.length() - 13; i++) {
            if (recordLine.charAt(i) == 'T' && recordLine.charAt(i+1) == 'o' && recordLine.charAt(i+2) == 't'
                    && recordLine.charAt(i+3) == 'a' && recordLine.charAt(i+4) == 'l'
                    && recordLine.charAt(i+5) == ' ' && recordLine.charAt(i+6) == 'V'
                    && recordLine.charAt(i+7) == 'a' && recordLine.charAt(i+8) == 'l'
                    && recordLine.charAt(i+9) == 'u' && recordLine.charAt(i+10) == 'e'
                    && recordLine.charAt(i+11) == ':' && recordLine.charAt(i+12) == ' ') {
                int k = i+13;
                String feed = "";
                while (recordLine.charAt(k) != '|') {
                    feed += recordLine.charAt(k);
                    k++;
                }
                coinPrices.add(Double.valueOf(feed));
                System.out.println("Adding " + feed);
            }
        }
        for (int i = 0; i < coinNames.size(); i++) {
            myPieDataset.setValue(coinNames.get(i), BigDecimal.valueOf(coinPrices.get(i)).setScale(2, RoundingMode.HALF_EVEN));
            }
        JFreeChart myChart = ChartFactory.createPieChart("Crypto Portfolio", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Chart", myChart);
        // allow the user to save the chart as an image
        javax.swing.JButton saveBTN = new javax.swing.JButton();
        saveBTN.setText("Save");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStream out = new FileOutputStream("chart.png");
                    ChartUtilities.writeChartAsPNG(out, myChart, chartFrame.getWidth(), chartFrame.getHeight());
                    JOptionPane.showMessageDialog(null, "Image saved to .png file.");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        // add text area
        javax.swing.JTextArea infoTA = new javax.swing.JTextArea();
        chartFrame.add(saveBTN);
        chartFrame.add(infoTA);
        chartFrame.setLayout(null);
        saveBTN.setBounds(chartFrame.getX(), chartFrame.getHeight(), 75, 20);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
        // calculate sum of all currency values
        double portfolioValue = 0;
        for (int i = 0; i < totalValues.size(); i++) {
            portfolioValue += totalValues.get(i);
        }
        infoTA.setText("Total value of portfolio: €" + BigDecimal.valueOf(portfolioValue).setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        summaryLBL = new javax.swing.JLabel();
        titleLBL = new javax.swing.JLabel();
        chartBTN = new javax.swing.JButton();
        databaseBTN = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryTA = new javax.swing.JTextArea();
        fetchDataBTN = new javax.swing.JButton();
        enterKeyBTN = new javax.swing.JButton();
        logoutBTN = new javax.swing.JButton();
        saveBTN = new javax.swing.JButton();
        loadBTN = new javax.swing.JButton();
        prevBTN = new javax.swing.JButton();
        nextBTN = new javax.swing.JButton();
        tutorialBTN = new javax.swing.JButton();
        modeCB = new javax.swing.JComboBox<>();
        settingBtn = new javax.swing.JButton();
        mainMB = new javax.swing.JMenuBar();
        menuMU = new javax.swing.JMenu();
        chartMI = new javax.swing.JMenuItem();
        databaseMI = new javax.swing.JMenuItem();
        chatMI = new javax.swing.JMenuItem();
        cryptoMU = new javax.swing.JMenu();
        pricesMI = new javax.swing.JMenuItem();
        keyMI = new javax.swing.JMenuItem();
        aboutMU = new javax.swing.JMenu();
        helpMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cryptochaun");
        setBackground(new java.awt.Color(0, 51, 204));
        setName("summaryFrame"); // NOI18N

        jPanel1.setBackground(new java.awt.Color(0, 102, 255));

        summaryLBL.setFont(new java.awt.Font("Segoe UI Black", 1, 12)); // NOI18N
        summaryLBL.setForeground(new java.awt.Color(255, 255, 255));
        summaryLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        summaryLBL.setText("Summary");

        titleLBL.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        titleLBL.setForeground(new java.awt.Color(255, 255, 255));
        titleLBL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLBL.setText("Summary");

        chartBTN.setText("Chart");
        chartBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chartBTNActionPerformed(evt);
            }
        });

        databaseBTN.setText("Database");
        databaseBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseBTNActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(0, 51, 204));

        summaryTA.setBackground(new java.awt.Color(242, 242, 242));
        summaryTA.setColumns(20);
        summaryTA.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        summaryTA.setLineWrap(true);
        summaryTA.setRows(5);
        summaryTA.setWrapStyleWord(true);
        jScrollPane1.setViewportView(summaryTA);

        fetchDataBTN.setText("Fetch Data");
        fetchDataBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fetchDataBTNActionPerformed(evt);
            }
        });

        enterKeyBTN.setText("Enter Key");
        enterKeyBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterKeyBTNActionPerformed(evt);
            }
        });

        logoutBTN.setText("Logout");

        saveBTN.setText("Save");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTNActionPerformed(evt);
            }
        });

        loadBTN.setText("Load");
        loadBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBTNActionPerformed(evt);
            }
        });

        prevBTN.setText("<");
        prevBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevBTNActionPerformed(evt);
            }
        });

        nextBTN.setText(">");
        nextBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextBTNActionPerformed(evt);
            }
        });

        tutorialBTN.setText("?");
        tutorialBTN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tutorialBTNActionPerformed(evt);
            }
        });

        modeCB.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "News", "Prices", "Leaderboard" }));
        modeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modeCBActionPerformed(evt);
            }
        });

        settingBtn.setText("Setting");
        settingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(fetchDataBTN)
                        .addGap(73, 73, 73)
                        .addComponent(enterKeyBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(logoutBTN)
                        .addGap(62, 62, 62)
                        .addComponent(saveBTN)
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(loadBTN))
                            .addComponent(chartBTN)
                            .addComponent(databaseBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(prevBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nextBTN)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(modeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(508, 508, 508)))
                .addGap(18, 18, 18))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(198, 198, 198)
                .addComponent(summaryLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(titleLBL, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)))
                .addGap(48, 48, 48)
                .addComponent(settingBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tutorialBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(titleLBL)
                        .addComponent(tutorialBTN, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(settingBtn)))
                .addGap(4, 4, 4)
                .addComponent(summaryLBL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(chartBTN)
                        .addGap(14, 14, 14)
                        .addComponent(databaseBTN)
                        .addGap(118, 118, 118))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(prevBTN)
                    .addComponent(nextBTN)
                    .addComponent(modeCB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fetchDataBTN)
                    .addComponent(enterKeyBTN)
                    .addComponent(logoutBTN)
                    .addComponent(saveBTN)
                    .addComponent(loadBTN))
                .addContainerGap())
        );

        menuMU.setText("Menu");

        chartMI.setText("Chart");
        chartMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chartMIActionPerformed(evt);
            }
        });
        menuMU.add(chartMI);

        databaseMI.setText("Database");
        databaseMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                databaseMIActionPerformed(evt);
            }
        });
        menuMU.add(databaseMI);

        chatMI.setText("Chat");
        chatMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatMIActionPerformed(evt);
            }
        });
        menuMU.add(chatMI);

        mainMB.add(menuMU);

        cryptoMU.setText("Crypto");

        pricesMI.setText("Get Prices");
        pricesMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pricesMIActionPerformed(evt);
            }
        });
        cryptoMU.add(pricesMI);

        keyMI.setText("Enter API Key");
        keyMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keyMIActionPerformed(evt);
            }
        });
        cryptoMU.add(keyMI);

        mainMB.add(cryptoMU);

        aboutMU.setText("About");

        helpMI.setText("Help");
        helpMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMIActionPerformed(evt);
            }
        });
        aboutMU.add(helpMI);

        mainMB.add(aboutMU);

        setJMenuBar(mainMB);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void prevBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevBTNActionPerformed
        // TODO add your handling code here:
        modeCB.setSelectedItem("News");
        if (articleIndex > 0)
            articleIndex--;
        else {
            articleIndex = 0;
        }
        NewsGUI news = new NewsGUI();
        news.setNewsFeed(newsEntries);
        news.readNews(articleIndex);
        news.setVisible(true);
    }//GEN-LAST:event_prevBTNActionPerformed

    private void nextBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBTNActionPerformed
        // TODO add your handling code here:
        modeCB.setSelectedItem("News");
        if (articleIndex < newsEntries.size() - 1)
            articleIndex++;
        NewsGUI news = new NewsGUI();
        news.setNewsFeed(newsEntries);
        news.readNews(articleIndex);
        news.setVisible(true);
    }//GEN-LAST:event_nextBTNActionPerformed

    private void databaseBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseBTNActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("editorFrame");
    }//GEN-LAST:event_databaseBTNActionPerformed

    private void chartBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartBTNActionPerformed
        // TODO add your handling code here:
        drawChart();
    }//GEN-LAST:event_chartBTNActionPerformed

    private void tutorialBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tutorialBTNActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("tutorialFrame");
    }//GEN-LAST:event_tutorialBTNActionPerformed

    private void enterKeyBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterKeyBTNActionPerformed
        // TODO add your handling code here:
        API_key = JOptionPane.showInputDialog(null, "Enter your API key: ");
        apiManager.setAPIKey(API_key);
        // test the key
        if (!API_key.equals("")) {
                if (apiManager.validateAPIKey())
                    JOptionPane.showMessageDialog(rootPane, "API key is valid!");
                else
                    JOptionPane.showMessageDialog(rootPane, "API key is invalid.");
        }
        if (API_key.equals(""))
            API_key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";
    }//GEN-LAST:event_enterKeyBTNActionPerformed

    private void fetchDataBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fetchDataBTNActionPerformed
        // TODO add your handling code here:
        if (apiManager.validateAPIKey() && !API_key.equals("b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c")) {
            apiManager.fetchAPI();
            summaryTA.setText(apiManager.getPrices());
        } else
            JOptionPane.showMessageDialog(null, "API key is invalid. Cannot fetch prices.");
        
        
    }//GEN-LAST:event_fetchDataBTNActionPerformed

    private void modeCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modeCBActionPerformed
        // TODO add your handling code here:
        if (modeCB.getSelectedItem().equals("News"))
            loadNews();
        else if (modeCB.getSelectedItem().equals("Prices"))
            summaryTA.setText(apiManager.getPrices());
        else if (modeCB.getSelectedItem().equals("Leaderboard")) {
            // connect to the leaderboard and add the user's timestamp to the
            // collection
            leaderboardConnection.connect("adminUser", "upPGU?7fZ+5d@4k"
                    ,"mycluster.eqvxj", "leaderboard_database", "leaderboard", false);
            if (leaderboardConnection.isConnected()) {
                leaderboardConnection.updateLeaderboard(((LoginGUI)guiManager.getFrame("loginFrame")).getUser());
                summaryTA.setText(leaderboardConnection.readLeaderboard());
            }
        }
    }//GEN-LAST:event_modeCBActionPerformed

    private void helpMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMIActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("tutorialFrame");
    }//GEN-LAST:event_helpMIActionPerformed

    private void chartMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chartMIActionPerformed
        // TODO add your handling code here:
        DefaultPieDataset myPieDataset = new DefaultPieDataset();
        myPieDataset.clear();
        FindIterable<Document> output = mongoManager.getCollection().find(new Document()).projection(exclude("_id"));
        List<Document> results = new ArrayList<>();
        output.into(results);
        // refresh values
        totalValues.clear();
        double price;
        if (apiManager.getObject() != null) {
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < apiManager.getJSONLength(); j++) {
                    if (apiManager.getObject().query(apiManager.getPointers().get(j).toString()).equals(results.get(i).getString("currency"))) {
                        price = Double.valueOf(apiManager.getObject().query(apiManager.getValues().get(j)).toString()) * Double.valueOf(results.get(i).getString("quantity"));
                        totalValues.add(price);
                    }
                }
            }
        }
        for (int i = 0; i < results.size(); i++) {
            myPieDataset.setValue(results.get(i).getString("currency"), BigDecimal.valueOf(totalValues.get(i)).setScale(2, RoundingMode.HALF_EVEN));
            }
        JFreeChart myChart = ChartFactory.createPieChart("My Crypto Portfolio", myPieDataset, rootPaneCheckingEnabled, rootPaneCheckingEnabled, Locale.ENGLISH);
        ChartFrame chartFrame = new ChartFrame("Chart", myChart);
        // allow the user to save the chart as an image
        javax.swing.JButton saveBTN = new javax.swing.JButton();
        saveBTN.setText("Save");
        saveBTN.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OutputStream out = new FileOutputStream("chart.png");
                    ChartUtilities.writeChartAsPNG(out, myChart, chartFrame.getWidth(), chartFrame.getHeight());
                    JOptionPane.showMessageDialog(null, "Image saved to .png file.");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        });
        // add text area
        javax.swing.JTextArea infoTA = new javax.swing.JTextArea();
        chartFrame.add(saveBTN);
        chartFrame.add(infoTA);
        chartFrame.setLayout(null);
        saveBTN.setBounds(chartFrame.getX(), chartFrame.getHeight(), 75, 20);
        chartFrame.setVisible(true);
        chartFrame.setSize(400, 500);
        // calculate sum of all currency values
        double portfolioValue = 0;
        for (int i = 0; i < totalValues.size(); i++) {
            portfolioValue += totalValues.get(i);
        }
        infoTA.setText("Total value of portfolio: €" + BigDecimal.valueOf(portfolioValue).setScale(2, RoundingMode.HALF_EVEN));
    }//GEN-LAST:event_chartMIActionPerformed

    private void databaseMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_databaseMIActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("editorFrame");
    }//GEN-LAST:event_databaseMIActionPerformed

    private void chatMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatMIActionPerformed
        // TODO add your handling code here:
        guiManager.loadFrame("chatFrame");
        LoginGUI loginScreen = (LoginGUI)guiManager.getFrame("loginFrame");
        String username = loginScreen.getUser();
        ChatGUI myChat = (ChatGUI)guiManager.getFrame("chatFrame");
        myChat.setUsername(username);
        myChat.setMongoDBManager(mongoManager);
        myChat.setAPIManager(apiManager);
    }//GEN-LAST:event_chatMIActionPerformed

    private void pricesMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pricesMIActionPerformed
        // TODO add your handling code here:
        apiManager.fetchAPI();
        summaryTA.setText(apiManager.getPrices());
    }//GEN-LAST:event_pricesMIActionPerformed

    private void keyMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keyMIActionPerformed
        // TODO add your handling code here:
        String API_key = JOptionPane.showInputDialog(null, "Enter your API key: ");
        apiManager.setAPIKey(API_key);
        
        if (API_key.equals(""))
            API_key = "b54bcf4d-1bca-4e8e-9a24-22ff2c3d462c";
    }//GEN-LAST:event_keyMIActionPerformed

    private void settingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingBtnActionPerformed
        guiManager.loadFrame("userSettingFrame");
    }//GEN-LAST:event_settingBtnActionPerformed

    private void loadBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBTNActionPerformed
        Properties config = FileManager.loadConfig();
    boolean shouldLoadAPI = Boolean.parseBoolean(config.getProperty("saveAPI", "false"));

    if (shouldLoadAPI) {
        Properties apiProps = FileManager.loadAPIData();
        String savedKey = apiProps.getProperty("apiKey");
        String savedData = apiProps.getProperty("apiData");

        if (savedKey != null && !savedKey.isEmpty()) {
            apiManager.setAPIKey(savedKey);
        }

        if (savedData != null && !savedData.isEmpty()) {
            try {
                apiManager.setObject(new JSONObject(savedData));
                summaryTA.setText(apiManager.getPrices());
            } catch (Exception e) {
                summaryTA.setText("Error loading saved API data.");
                e.printStackTrace();
            }
        } else {
            summaryTA.setText("No saved API data found.");
        }
    } else {
        summaryTA.setText("saveAPI setting is off. Data not loaded.");
    }
    }//GEN-LAST:event_loadBTNActionPerformed

    private void saveBTNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTNActionPerformed
    Properties config = FileManager.loadConfig();
    boolean shouldSaveAPI = Boolean.parseBoolean(config.getProperty("saveAPI", "false"));

    if (shouldSaveAPI) {
        String apiKey = apiManager.getAPIKey();
        String apiData = apiManager.getObject().toString();  // raw JSON
        FileManager.saveAPIData(apiKey, apiData);
    } else {
        JOptionPane.showMessageDialog(null, "saveAPI setting is disabled in config. Data not saved.");
    }

    }//GEN-LAST:event_saveBTNActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SummaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SummaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SummaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SummaryGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SummaryGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu aboutMU;
    private javax.swing.JButton chartBTN;
    private javax.swing.JMenuItem chartMI;
    private javax.swing.JMenuItem chatMI;
    private javax.swing.JMenu cryptoMU;
    private javax.swing.JButton databaseBTN;
    private javax.swing.JMenuItem databaseMI;
    private javax.swing.JButton enterKeyBTN;
    private javax.swing.JButton fetchDataBTN;
    private javax.swing.JMenuItem helpMI;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem keyMI;
    private javax.swing.JButton loadBTN;
    private javax.swing.JButton logoutBTN;
    private javax.swing.JMenuBar mainMB;
    private javax.swing.JMenu menuMU;
    private javax.swing.JComboBox<String> modeCB;
    private javax.swing.JButton nextBTN;
    private javax.swing.JButton prevBTN;
    private javax.swing.JMenuItem pricesMI;
    private javax.swing.JButton saveBTN;
    private javax.swing.JButton settingBtn;
    private javax.swing.JLabel summaryLBL;
    private javax.swing.JTextArea summaryTA;
    private javax.swing.JLabel titleLBL;
    private javax.swing.JButton tutorialBTN;
    // End of variables declaration//GEN-END:variables
}
