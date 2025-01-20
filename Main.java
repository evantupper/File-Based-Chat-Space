import java.util.ArrayList;
import java.util.Random;



import javax.swing.JOptionPane;

import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.TrueTypeFont;

import java.net.NetworkInterface;
import java.util.Collections;

import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.ptr.IntByReference;

import java.awt.Font;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

import tupper.tsdn.PacketManager;


public class Main extends BasicGame {
    //Configuration
    private final static int SCREEN_WIDTH = 1680;
    private final static int SCREEN_HEIGHT = 960;
    private static int FRAMERATE = 60; //1:1 to tick speed


    //Lists
    public static ArrayList<Player> playerArrayList = new ArrayList<Player>();
    public static ArrayList<TextParticle> textArrayList = new ArrayList<TextParticle>();
    public static ArrayList<Particle> partArrayList = new ArrayList<Particle>();
    public static ArrayList<Block> blockArrayList = new ArrayList<Block>();

    //Security & Network
    private static byte[] mac;
    private static int sysID;
    private static int offset_id = new Random().nextInt(10);
    static PacketManager pam;

    //Misc
    public Random randomGen = new Random();
    private Shape black_right_cover;
    public static TrueTypeFont font;

    public Main(String title){
        super(title);
        
        mac = null;
        try {
            ArrayList<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface iface : interfaces) {
                if (iface.isUp()) {
                    if (iface.getHardwareAddress() != null) {
                        mac = iface.getHardwareAddress();
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }


        pam = new PacketManager(".\\chat_rooms\\",true);
        sysID = pam.getSystemID();
    }

    public void pushMessage(String m) {
        try {
            playerArrayList.get(0).messages.add(new Message(URLDecoder.decode(m, "UTF-8"), 180+m.length()*6));
        } catch(Exception e) {
            e.printStackTrace();
        }

    } 


    Shape circle;
    Color col;
    public void inter(GameContainer container, Graphics g) {
        pam.clear();
        pam.obtainAll();
        for (int i = 0; i < pam.packets.size(); i++)
            if (pam.packets.get(i).getID() != sysID) {
                final String[] parse = pam.packets.get(i).getData().split("`");
                //parse ;
                int px = Integer.parseInt(parse[0]);
                int py = Integer.parseInt(parse[1]);
                final int bound = Integer.parseInt(parse[4])*2;

                new Player(parse[2],Integer.parseInt(parse[3])) {{
                        setX(px);
                        setY(py);
                        try {

                            for (int i = 0; i < bound; i+=2) {
                                if (parse[5].indexOf("$sudo") == 0) {
                                    pushMessage(parse[5]);
                                }
                                else {
                                    messages.add(new Message(URLDecoder.decode(parse[5+i], "UTF-8"), Integer.parseInt(parse[6+i])));
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        } 

                        render(container, g);
                    }};
            }
        forcePlayer();

    }

    @Override
    public void init(GameContainer container) throws SlickException {
        font = new TrueTypeFont(new Font("Microsoft Himalaya", Font.PLAIN, 24), false);
        try {
            container.setMouseCursor(new Image(".\\res\\masks for  particles\\Cursor.png"), 1, 1);
        } catch (SlickException e) {
            e.printStackTrace();
        }

        black_right_cover = new Rectangle(1281,0,400,960);

        String name;
        char[] charList = new char[100];
        Secur32.INSTANCE.GetUserNameEx(3, charList, new IntByReference(charList.length));
        name = (new String(charList)).trim();
        playerArrayList.add(new Player(name, new Random().nextInt(99)));

    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {

        for (Player childPlayer : playerArrayList) 
            childPlayer.update(container);
        for (TextParticle childTPart : textArrayList) 
            childTPart.update();
        for (Particle childTPart : partArrayList) 
            childTPart.update();

        for(int i = textArrayList.size()-1; i > -1; i--) 
            if (!textArrayList.get(i).isAlive()) 
                textArrayList.remove(i);

        for(int i = partArrayList.size()-1; i > -1; i--) 
            if (!partArrayList.get(i).isAlive()) 
                partArrayList.remove(i);

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setFont(font);
        g.setColor(Color.white);

        for (Particle childTPart : partArrayList) 
            childTPart.render(container, g);
        inter(container, g);
        for (Player childPlayer : playerArrayList) 
            childPlayer.render(container,g);
        for (TextParticle childTPart : textArrayList) 
            childTPart.render(container, g);

        g.setColor(Color.black);
        g.fill(black_right_cover);

        g.setColor(Color.white);
        g.drawString("Current Room: "+pam.getRoom(), 1300, 40 );
        for (int i = 1; i <= 20; i++) {
            try {
                g.drawString("Room "+i+" Player Count: "+pam.sense(i), 1300, 60 + 15*i);
            }
            catch (Exception e) {
                g.drawString("Room "+i+" currently not made.", 1300, 60 + 15*i);
            }
        }

    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Online Chat Space")); //[R] //

        app.setAlwaysRender(true);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false); //[R]
        app.setTargetFrameRate(FRAMERATE-1);

        app.setShowFPS(false);
        app.start(); //[R]
    }

    public static void forcePlayer() {
        String mass = "";
        for (int i = 0; i < playerArrayList.get(0).messages.size(); i++) {
            try {
                mass+="`"+URLEncoder.encode(playerArrayList.get(0).messages.get(i).message, "UTF-8") + "`" + playerArrayList.get(0).messages.get(i).frames;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pam.changeHeldData(playerArrayList.get(0).getX()+
                "`"+playerArrayList.get(0).getY()+
                "`"+playerArrayList.get(0).getName()+
                "`"+playerArrayList.get(0).getChar()+
                "`"+(playerArrayList.get(0).messages.size()+1)+"`"+playerArrayList.get(0).currentMessage+"`"+1+ mass);
        pam.force();
    }

    public static void pushPlayer() {
        String mass = "";
        for (int i = 0; i < playerArrayList.get(0).messages.size(); i++) {
            try {
                mass+="`"+URLEncoder.encode(playerArrayList.get(0).messages.get(i).message, "UTF-8") + "`" + playerArrayList.get(0).messages.get(i).frames;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        pam.changeHeldData(playerArrayList.get(0).getX()+
                "`"+playerArrayList.get(0).getY()+
                "`"+playerArrayList.get(0).getName()+
                "`"+playerArrayList.get(0).getChar()+
                "`"+playerArrayList.get(0).messages.size()+ mass
            );
        pam.force();
    }

    @Override
    public boolean closeRequested()
    {
        pam.wipe();
        System.exit(0); 
        return false;
    }

    public static int getSysID() {
        return sysID;
    }

    public static PacketManager getPam() {
        return pam;
    }

    public static int[] getDimensions() {
        return new int[] {SCREEN_WIDTH, SCREEN_HEIGHT};
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static ArrayList<Block> getBlocks() {
        return blockArrayList;
    }

    public static ArrayList<Player> getPlayer() {
        return playerArrayList;
    }

}