import java.util.Random;

import com.sun.jna.platform.win32.Secur32;
import com.sun.jna.ptr.IntByReference;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.*;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import org.newdawn.slick.state.*;
import org.newdawn.slick.*;
import java.awt.Font;

import java.util.ArrayList;
import java.io.InputStream;
import org.newdawn.slick.util.ResourceLoader;

public class Player {
    private Shape circle = null; 

    private int colorID;
    private int x = 100;
    private int y = 100;

    private double velocityX = 0;
    private double velocityY = 0;
    int speed = 2;
    int accelspeed = 2;

    private String name;
    private Image characters;
    private final int PLAYER_WIDTH = 15;
    private boolean changerHeld;

    public Player(String name, int colorID) {
        circle = new Circle(x,y, PLAYER_WIDTH);
        this.colorID = colorID;
        setColorID(colorID);
        this.name = name;

        try {
            characters = new Image("res/100 smileys2.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }

        // load a default java font

        /* Centaur
        Gabriola
        Bookman Old Style
        Papyrus
        OCR A EXTENDED
        Microsoft Himalaya
         */
    }


    
    public ArrayList<Message> messages = new ArrayList<Message>();
    public void update(GameContainer container) {
        if (velocityX > 0) velocityX-=0.1*accelspeed;
        else if (velocityX < 0) velocityX+=0.1*accelspeed;
        if (velocityY > 0) velocityY-=0.1*accelspeed;
        else if (velocityY < 0) velocityY+=0.1*accelspeed;
        input(container);
        if (velocityX > 3*speed) velocityX = 3*speed;
        else if (velocityX < -3*speed) velocityX = -3*speed;
        if (velocityY > 3*speed) velocityY = 3*speed;
        else if (velocityY < -3*speed) velocityY = -3*speed;

        x+=(int)velocityX;
        y+=(int)velocityY;
        if (x>1280-PLAYER_WIDTH) x = 1280-PLAYER_WIDTH;
        else if (x<0+PLAYER_WIDTH) x = 0+PLAYER_WIDTH;
        if (y>960-PLAYER_WIDTH) y = 960-PLAYER_WIDTH;
        else if (y<0+PLAYER_WIDTH) y = 0+PLAYER_WIDTH;
        circle.setCenterX(x);
        circle.setCenterY(y);
    }

    private int img_size = 30;
    int doubled_player_width = PLAYER_WIDTH * 2;
    public void render(GameContainer container, Graphics g) {
        g.drawString(
            name, 
            x - Main.font.getWidth(name)/2, 
            y + Main.font.getHeight(name)/2 + 5
        );
        g.drawString(
            currentMessage, 
            x - Main.font.getWidth(currentMessage)/2,
            y - Main.font.getHeight(currentMessage) - 10
        );
        characters.getSubImage(
            (colorID%10)*img_size, 
            (colorID/10)*img_size, 
            img_size, 
            img_size
        ).getScaledCopy(doubled_player_width, doubled_player_width)
        .drawCentered(x, y);

        for (int i = messages.size() - 1; i >= 0; i--) 
            if (!messages.get(i).update()) 
                messages.remove(i);
        for (int i = 0; i < messages.size(); i++)
            g.drawString(messages.get(i).message, x - Main.font.getWidth(messages.get(i).message)/2, y - Main.font.getHeight(messages.get(i).message) * (i+2));
    }

    private boolean r_held = false;
    private boolean t_held = false;

    private boolean typing = false;
    private boolean enter_held = false;
    public String currentMessage = "";

    private boolean caps = false;
    private boolean caps_held = false;
    Input input;
    public void input(GameContainer container) {
        input = container.getInput();
        if (typing) {
            typing = true;
            //if (Mouse.isButtonDown(0) ) messages.add(new Message("Hello!! :D", 240));
            if (caps) {
                if (input.isKeyPressed(Input.KEY_A)) currentMessage += "A";
                else if (input.isKeyPressed(Input.KEY_B)) currentMessage += "B";
                else if (input.isKeyPressed(Input.KEY_C)) currentMessage += "C";
                else if (input.isKeyPressed(Input.KEY_D)) currentMessage += "D";
                else if (input.isKeyPressed(Input.KEY_E)) currentMessage += "E";
                else if (input.isKeyPressed(Input.KEY_F)) currentMessage += "F";
                else if (input.isKeyPressed(Input.KEY_G)) currentMessage += "G";
                else if (input.isKeyPressed(Input.KEY_H)) currentMessage += "H";
                else if (input.isKeyPressed(Input.KEY_I)) currentMessage += "I";
                else if (input.isKeyPressed(Input.KEY_J)) currentMessage += "J";
                else if (input.isKeyPressed(Input.KEY_K)) currentMessage += "K";
                else if (input.isKeyPressed(Input.KEY_L)) currentMessage += "L";
                else if (input.isKeyPressed(Input.KEY_M)) currentMessage += "M";
                else if (input.isKeyPressed(Input.KEY_N)) currentMessage += "N";
                else if (input.isKeyPressed(Input.KEY_O)) currentMessage += "O";
                else if (input.isKeyPressed(Input.KEY_P)) currentMessage += "P";
                else if (input.isKeyPressed(Input.KEY_Q)) currentMessage += "Q";
                else if (input.isKeyPressed(Input.KEY_R)) currentMessage += "R";
                else if (input.isKeyPressed(Input.KEY_S)) currentMessage += "S";
                else if (input.isKeyPressed(Input.KEY_T)) currentMessage += "T";
                else if (input.isKeyPressed(Input.KEY_U)) currentMessage += "U";
                else if (input.isKeyPressed(Input.KEY_V)) currentMessage += "V";
                else if (input.isKeyPressed(Input.KEY_W)) currentMessage += "W";
                else if (input.isKeyPressed(Input.KEY_X)) currentMessage += "X";
                else if (input.isKeyPressed(Input.KEY_Y)) currentMessage += "Y";
                else if (input.isKeyPressed(Input.KEY_Z)) currentMessage += "Z";
                else if (input.isKeyPressed(Input.KEY_1)) currentMessage += "!";
                else if (input.isKeyPressed(Input.KEY_2)) currentMessage += "@";
                else if (input.isKeyPressed(Input.KEY_3)) currentMessage += "#";
                else if (input.isKeyPressed(Input.KEY_4)) currentMessage += "$";
                else if (input.isKeyPressed(Input.KEY_5)) currentMessage += "%";
                else if (input.isKeyPressed(Input.KEY_6)) currentMessage += "^";
                else if (input.isKeyPressed(Input.KEY_7)) currentMessage += "&";
                else if (input.isKeyPressed(Input.KEY_8)) currentMessage += "*";
                else if (input.isKeyPressed(Input.KEY_9)) currentMessage += "(";
                else if (input.isKeyPressed(Input.KEY_0)) currentMessage += ")";
                else if (input.isKeyPressed(Input.KEY_MINUS)) currentMessage += "_";
                else if (input.isKeyPressed(Input.KEY_EQUALS)) currentMessage += "+";
                else if (input.isKeyPressed(Input.KEY_LBRACKET)) currentMessage += "{";
                else if (input.isKeyPressed(Input.KEY_RBRACKET)) currentMessage += "}";
                else if (input.isKeyPressed(Input.KEY_BACKSLASH)) currentMessage += "|";
                else if (input.isKeyPressed(Input.KEY_SEMICOLON)) currentMessage += ":";
                else if (input.isKeyPressed(Input.KEY_APOSTROPHE)) currentMessage += "\"";
                else if (input.isKeyPressed(Input.KEY_COMMA)) currentMessage += "<";
                else if (input.isKeyPressed(Input.KEY_PERIOD)) currentMessage += ">";
                else if (input.isKeyPressed(Input.KEY_SLASH)) currentMessage += "?";
            }
            else {
                if (input.isKeyPressed(Input.KEY_A)) currentMessage += "a";
                else if (input.isKeyPressed(Input.KEY_B)) currentMessage += "b";
                else if (input.isKeyPressed(Input.KEY_C)) currentMessage += "c";
                else if (input.isKeyPressed(Input.KEY_D)) currentMessage += "d";
                else if (input.isKeyPressed(Input.KEY_E)) currentMessage += "e";
                else if (input.isKeyPressed(Input.KEY_F)) currentMessage += "f";
                else if (input.isKeyPressed(Input.KEY_G)) currentMessage += "g";
                else if (input.isKeyPressed(Input.KEY_H)) currentMessage += "h";
                else if (input.isKeyPressed(Input.KEY_I)) currentMessage += "i";
                else if (input.isKeyPressed(Input.KEY_J)) currentMessage += "j";
                else if (input.isKeyPressed(Input.KEY_K)) currentMessage += "k";
                else if (input.isKeyPressed(Input.KEY_L)) currentMessage += "l";
                else if (input.isKeyPressed(Input.KEY_M)) currentMessage += "m";
                else if (input.isKeyPressed(Input.KEY_N)) currentMessage += "n";
                else if (input.isKeyPressed(Input.KEY_O)) currentMessage += "o";
                else if (input.isKeyPressed(Input.KEY_P)) currentMessage += "p";
                else if (input.isKeyPressed(Input.KEY_Q)) currentMessage += "q";
                else if (input.isKeyPressed(Input.KEY_R)) currentMessage += "r";
                else if (input.isKeyPressed(Input.KEY_S)) currentMessage += "s";
                else if (input.isKeyPressed(Input.KEY_T)) currentMessage += "t";
                else if (input.isKeyPressed(Input.KEY_U)) currentMessage += "u";
                else if (input.isKeyPressed(Input.KEY_V)) currentMessage += "v";
                else if (input.isKeyPressed(Input.KEY_W)) currentMessage += "w";
                else if (input.isKeyPressed(Input.KEY_X)) currentMessage += "x";
                else if (input.isKeyPressed(Input.KEY_Y)) currentMessage += "y";
                else if (input.isKeyPressed(Input.KEY_Z)) currentMessage += "z";
                else if (input.isKeyPressed(Input.KEY_1)) currentMessage += "1";
                else if (input.isKeyPressed(Input.KEY_2)) currentMessage += "2";
                else if (input.isKeyPressed(Input.KEY_3)) currentMessage += "3";
                else if (input.isKeyPressed(Input.KEY_4)) currentMessage += "4";
                else if (input.isKeyPressed(Input.KEY_5)) currentMessage += "5";
                else if (input.isKeyPressed(Input.KEY_6)) currentMessage += "6";
                else if (input.isKeyPressed(Input.KEY_7)) currentMessage += "7";
                else if (input.isKeyPressed(Input.KEY_8)) currentMessage += "8";
                else if (input.isKeyPressed(Input.KEY_9)) currentMessage += "9";
                else if (input.isKeyPressed(Input.KEY_0)) currentMessage += "0";
                else if (input.isKeyPressed(Input.KEY_MINUS)) currentMessage += "-";
                else if (input.isKeyPressed(Input.KEY_EQUALS)) currentMessage += "=";
                else if (input.isKeyPressed(Input.KEY_LBRACKET)) currentMessage += "[";
                else if (input.isKeyPressed(Input.KEY_RBRACKET)) currentMessage += "]";
                else if (input.isKeyPressed(Input.KEY_BACKSLASH)) currentMessage += "\\";
                else if (input.isKeyPressed(Input.KEY_SEMICOLON)) currentMessage += ";";
                else if (input.isKeyPressed(Input.KEY_APOSTROPHE)) currentMessage += "\'";
                else if (input.isKeyPressed(Input.KEY_COMMA)) currentMessage += ",";
                if (input.isKeyPressed(Input.KEY_PERIOD)) currentMessage += ".";
                if (input.isKeyPressed(Input.KEY_SLASH)) currentMessage += "/";
            }
            if (input.isKeyPressed(Input.KEY_SPACE))
                currentMessage += " ";
            if (input.isKeyPressed(Input.KEY_BACK) && currentMessage.length() > 0) 
                currentMessage = currentMessage.substring(0, currentMessage.length() - 1);
            

            

            if (input.isKeyDown(Input.KEY_LSHIFT) && !caps_held) {
                caps = true;
                caps_held = true;
            }
            else if (!input.isKeyDown(Input.KEY_LSHIFT) && caps_held) {
                caps = false;
                caps_held = false;
            }

            if (input.isKeyDown(Input.KEY_ENTER) && !enter_held) {
                typing = false;
                if (currentMessage.indexOf("$rename") == 0) {
                    name = currentMessage.substring(7).trim();
                }
                else if (currentMessage.indexOf("$sudo") == 0) {
                    messages.add(new Message(currentMessage.trim(), 2));
                }
                else {
                    messages.add(new Message(currentMessage,180+currentMessage.length()*6));
                }

                currentMessage = "";
                enter_held = true;
                return;
            }
            else if (!input.isKeyDown(Input.KEY_ENTER) && enter_held) {
                enter_held = false;
                currentMessage = "";
            }

            if (input.isKeyDown(Input.KEY_ESCAPE)) {
                typing = false;
                currentMessage = "";
            }
            return;
        }
        else if (!typing) {
            typing = false;
            if (input.isKeyDown(Input.KEY_W)) velocityY-=0.3*accelspeed;
            if (input.isKeyDown(Input.KEY_S)) velocityY+=0.3*accelspeed;
            if (input.isKeyDown(Input.KEY_D)) velocityX+=0.3*accelspeed;
            if (input.isKeyDown(Input.KEY_A)) velocityX-=0.3*accelspeed;

            if (input.isKeyDown(Input.KEY_R) && !r_held) {
                r_held = true;
                Main.getPam().swapTo(Main.getPam().getRoom() - 1);
            }
            else if (!input.isKeyDown(Input.KEY_R) && r_held) 
                r_held = false;
            if (input.isKeyDown(Input.KEY_T) && !t_held) {
                t_held = true;
                Main.getPam().swapTo(Main.getPam().getRoom() + 1);
            }
            else if (!input.isKeyDown(Input.KEY_T) && t_held) 
                t_held = false;

            if (input.isKeyDown(Input.KEY_1) && !changerHeld) {
                changerHeld = true;
                setColorID(colorID-1);
            }
            else if (input.isKeyDown(Input.KEY_2) && !changerHeld) {
                changerHeld = true;
                setColorID(colorID+1);
            }
            else if (!input.isKeyDown(Input.KEY_2) && !input.isKeyDown(Input.KEY_1))
                changerHeld = false;

            if (input.isKeyDown(Input.KEY_ENTER) && !enter_held) {
                enter_held = true;
                typing = true;
                currentMessage = "";
                return;
            }
            else if (!input.isKeyDown(Input.KEY_ENTER) && enter_held) {
                typing = false;
                enter_held = false;
                currentMessage = "";
            }

        }

    }

    public int getColorID() {
        return colorID;
    }

    public void setColorID(int id) {
        this.colorID = Math.min(Math.max(0,id), 100);
    }

    public Shape getShape() {
        return circle;
    }

    public void setX(int nx) {
        x = nx;
    }

    public void setY(int ny) {
        y = ny;
    }

    public int getChar() {
        return colorID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
