/*
Copyright (c) 2005-2008 Kasidit Yusuf
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
1. Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
   derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package DaysToDay;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


public class DaysToDay extends MIDlet implements CommandListener, ItemStateListener {
    
    /**
     * Creates a new instance of DaysToDay 
     */
    static final long LDAYLENGTH = 86400000L;
    static final long LWEEKLENGTH = 604800000L;
    Canvas waitCanvas;
    boolean cancelSearch;
    public final char[] LoadingStr = {'L','o','a','d','i','n','g'};
    public final char[] SearchingStr = {'S','e','a','r','c','h','i','n','g'};
    public char[] ResultStr = {' ','R','e','s','u','l','t','s',':',' ',' ',' ',' ',' ',' ',' ',' '};
    
    boolean started;
    boolean registered;
    Command cancelCommand,nextPageCommand,prevPageCommand;
    int dow=0,d=0,m=0,y=0,range=0,turner;
    public static DaysToDay curInstance;    
    Image img;
    Vector searchV;
    Font f,sf;
    Displayable screenAfterResult;
        
    
    public DaysToDay() {
        started = false;
        registered = false;
        try{ 
        img = Image.createImage("/DaysToDay/dtdlogobig.png");       
        }
        catch(Exception e){img = null;}
        f = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD, Font.SIZE_MEDIUM);
        sf = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
        waitCanvas = new Canvas(){
            public void paint(Graphics g)
            {
        
                g.setColor(0xffffff);
        
                g.fillRect(0,0, this.getWidth(),this.getHeight());
        
                if(img!=null)
                    g.drawImage(img,(getWidth() - img.getWidth())/2,(getHeight() - img.getHeight())/2,g.TOP|g.LEFT);
        
                g.setColor(0);
                g.setFont(f);
                
                
                if(!started)
                    g.drawChars(LoadingStr,  0,  LoadingStr.length,  this.getWidth()/2, (4*this.getHeight())/5,g.HCENTER|g.TOP);
                else
                {          
                    g.drawChars(SearchingStr, 0, SearchingStr.length, this.getWidth()/2, (4*this.getHeight())/5,g.HCENTER|g.TOP);
                    g.setFont(sf);
                    if(numRes>999)
                        ResultStr[10] =(char)(48+(numRes/1000));
                    else
                        ResultStr[10] =' ';
                    
                    if(numRes>99)
                        ResultStr[11] =(char)(48+((numRes%1000)/100));
                    else
                        ResultStr[11] =' ';
                    
                    if(numRes>9)
                        ResultStr[12] =(char)(48+((numRes%100)/10));
                    else
                        ResultStr[12] =' ';
                    
                    
                        ResultStr[13] =(char) (48+ (numRes%10));
                    
                   
                    
                        switch(turner++%8)
                        {
                            case 0: ResultStr[ResultStr.length-2] = ' ';ResultStr[ResultStr.length-1] = '\\';break;
                            case 1: ResultStr[ResultStr.length-2] = '\\';ResultStr[ResultStr.length-1] = '\\';break;                            
                            case 2: ResultStr[ResultStr.length-2] = '-';ResultStr[ResultStr.length-1] = '-';break;
                            case 3: ResultStr[ResultStr.length-2] = '=';ResultStr[ResultStr.length-1] = '=';break;
                            case 4: ResultStr[ResultStr.length-2] = '/';ResultStr[ResultStr.length-1] = ' ';break;
                            case 5: ResultStr[ResultStr.length-2] = '/';ResultStr[ResultStr.length-1] = '/';break;
                            case 6: ResultStr[ResultStr.length-2] = '|';ResultStr[ResultStr.length-1] = ' ';break;
                            case 7: ResultStr[ResultStr.length-2] = '|';ResultStr[ResultStr.length-1] = '|';break;
                        }
                    if(turner == 8)
                        turner = 0;
                    
                    g.drawChars(ResultStr, 0, ResultStr.length, this.getWidth()/2, (this.getHeight())/8,g.HCENTER|g.TOP);
                }
        
                    
            }
        
        };
        Display.getDisplay(this).setCurrent(waitCanvas);
        searchV = new Vector();        
        waitCanvas.repaint();
        waitCanvas.serviceRepaints();
        initialize();
        DOWList = new javax.microedition.lcdui.List("Select DayOfweek", javax.microedition.lcdui.Choice.IMPLICIT, new java.lang.String[] {
                "Sunday",
                        "Monday",
                        "Tuesday",
                        "Wednesday",
                        "Thursday",
                        "Friday",
                        "Saturday"
            }, null);
            
            DOWList.addCommand(get_backCommand1());
            DOWList.addCommand(get_helpCommand1());
            //DOWList.addCommand(get_exitCommand1());
            DOWList.setCommandListener(new CommandListener(){
            
            public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable)
            {
                HandleCmd(command, displayable);
            } 
            });
            nextPageCommand = new Command("Next Page",Command.SCREEN, 1);
            prevPageCommand = new Command("Prev Page",Command.SCREEN, 2);
            ResultList = new javax.microedition.lcdui.List("Result", javax.microedition.lcdui.Choice.IMPLICIT);            
            ResultList.addCommand(get_backCommand1());
            ResultList.addCommand(get_helpCommand1());
            
            
            //DOWList.addCommand(get_exitCommand1());
            ResultList.setCommandListener(new CommandListener(){
            
            public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable)
            {
                HandleCmd(command, displayable);
            } 
            
            });
            get_Menu();
            cancelCommand = new Command("Cancel",Command.CANCEL, 1);            
            //Display.getDisplay(this).setCurrent(Menu);
            waitCanvas.addCommand(cancelCommand);
            waitCanvas.setCommandListener(new CommandListener(){public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable){cancelSearch = true;}});            
            started = true;
            
            curInstance = this;
            
            showIntro("This software is FREE! Please help the poor, the orphans and spend in charity! Please do good and stay away from bad deeds! May God Guide & Bless.");
    }
    
    private Ticker ticker1;//GEN-BEGIN:MVDFields
    private Alert theAlert;
    private List Menu;
    private Command helpCommand1;
    private Command exitCommand1;
    private Form DateForm1;
    private Form DateForm2;
    private Form LeapYearForm;
    private Command backCommand1;
    private Command okCommand1;
    private Command selectCommand1;
    private Form TwoNumberForm;
    private TextField lyearTF;
    private TextField textField2;
    private TextField textField3;
    private TextField textField4;
    private TextField textField5;
    private TextField textField6;
    private TextField textField7;
    private TextField textField8;
    private TextField textField9;
    private TextField textField10;
    private TextField textField11;
    private TextField textField12;
    private Form SearchEngineForm;
    private StringItem stringItem1;
    private ChoiceGroup dowCG;
    private StringItem stringItem2;
    private ChoiceGroup dCG;
    private StringItem stringItem3;
    private ChoiceGroup rCG;
    private ChoiceGroup choiceGroup4;
    private ChoiceGroup mCG;
    private TextField yTF;
    private Form OneNumberForm;
    private TextField textField13;
    private StringItem stringItem4;
    private Form ResultForm;
    private StringItem resultSI;
    private Command doneCommand;//GEN-END:MVDFields

//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
//GEN-LINE:MVDInitInit
        // Insert post-init code here
    }//GEN-LINE:MVDInitEnd

    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay () {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay (this);
    }//GEN-LAST:MVDGetDisplay

    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet () {//GEN-FIRST:MVDExitMidlet
        getDisplay ().setCurrent (null);
        destroyApp (true);
        notifyDestroyed ();
    }//GEN-LAST:MVDExitMidlet

    /** Called by the system to indicate that a command has been invoked on a particular displayable.//GEN-BEGIN:MVDCABegin
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:MVDCABegin
    // Insert global pre-action code here
        if (displayable == Menu) {//GEN-BEGIN:MVDCABody
            if (command == exitCommand1) {//GEN-END:MVDCABody
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction8
                // Insert post-action code here
            } else if (command == Menu.SELECT_COMMAND) {//GEN-BEGIN:MVDCACase8
                switch (get_Menu().getSelectedIndex()) {
                    case 0://GEN-END:MVDCACase8
                        CountDown();
                        // Do nothing//GEN-LINE:MVDCAAction10
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase10
                    case 1://GEN-END:MVDCACase10
                        CountToFuture();
                        // Do nothing//GEN-LINE:MVDCAAction14
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase14
                    case 2://GEN-END:MVDCACase14
                        CountToPast();
                        // Do nothing//GEN-LINE:MVDCAAction16
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase16
                    case 3://GEN-END:MVDCACase16
                        BetweenDates();
                        // Do nothing//GEN-LINE:MVDCAAction12
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase12
                    case 4://GEN-END:MVDCACase12
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_SearchEngineForm());//GEN-LINE:MVDCAAction18
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase18
                    case 5://GEN-END:MVDCACase18
                        NextDOW();
                        // Do nothing//GEN-LINE:MVDCAAction20
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase20
                    case 6://GEN-END:MVDCACase20
                        LastDOW();
                        // Do nothing//GEN-LINE:MVDCAAction26
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase26
                    case 7://GEN-END:MVDCACase26
                        DPD();
                        // Do nothing//GEN-LINE:MVDCAAction24
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase24
                    case 8://GEN-END:MVDCACase24
                        DMD();
                        // Do nothing//GEN-LINE:MVDCAAction28
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase28
                    case 9://GEN-END:MVDCACase28
                        // Insert pre-action code here
                        getDisplay().setCurrent(get_LeapYearForm());//GEN-LINE:MVDCAAction22
                        // Insert post-action code here
                        break;//GEN-BEGIN:MVDCACase22
                    case 10://GEN-END:MVDCACase22
                        
                        // Do nothing//GEN-LINE:MVDCAAction105
                       showHelp("Use DaysToDay for quick, accurate and professional calendar counting tasks.\n Please select the desired function (and press the joystick \"IN\" or \"FIRE\" for some devices) to start. Further help is provided in each screen.\n Please visit www.clearevo.com for further details.\n");                        
                       break;//GEN-BEGIN:MVDCACase105
                    case 11://GEN-END:MVDCACase105
                       showAbout();
                       // Do nothing//GEN-LINE:MVDCAAction30
                       
                       break;//GEN-BEGIN:MVDCACase30
                }
            }
        } else if (displayable == SearchEngineForm) {
            if (command == helpCommand1) {//GEN-END:MVDCACase30
                HandleCmd(command,displayable);
                // Do nothing//GEN-LINE:MVDCAAction96
                // Insert post-action code here
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase96
                // Insert pre-action code here
                getDisplay().setCurrent(get_Menu());//GEN-LINE:MVDCAAction97
                // Insert post-action code here
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase97
                HandleCmd(command,displayable);
                // Do nothing//GEN-LINE:MVDCAAction95
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase95
        } else if (displayable == OneNumberForm) {
            if (command == helpCommand1) {//GEN-END:MVDCACase95
                HandleCmd(command,displayable);
                // Do nothing//GEN-LINE:MVDCAAction103
                // Insert post-action code here
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase103
                HandleCmd(command,displayable);
                // Do nothing//GEN-LINE:MVDCAAction102
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase102
        } else if (displayable == DateForm1) {
            if (command == helpCommand1) {//GEN-END:MVDCACase102
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction37
                HandleCmd(command,displayable);
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase37
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction41
                HandleCmd(command,displayable);
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase41
                // Insert pre-action code here
                getDisplay().setCurrent(get_Menu());//GEN-LINE:MVDCAAction36
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase36
        } else if (displayable == TwoNumberForm) {
            if (command == helpCommand1) {//GEN-END:MVDCACase36
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction53
                HandleCmd(command,displayable);
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase53
                // Insert pre-action code here
                getDisplay().setCurrent(get_Menu());//GEN-LINE:MVDCAAction52
                // Insert post-action code here
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase52
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction51
                HandleCmd(command,displayable);
            }//GEN-BEGIN:MVDCACase51
        } else if (displayable == LeapYearForm) {
            if (command == helpCommand1) {//GEN-END:MVDCACase51
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction45
                HandleCmd(command,displayable);
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase45
                // Insert pre-action code here
                getDisplay().setCurrent(get_Menu());//GEN-LINE:MVDCAAction44
                // Insert post-action code here
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase44
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction43
                int y = 0;
                
                try{
                    y = Integer.parseInt(lyearTF.getString());
                    if(y == 0) {
                        showError("Year 0 does not exist.",LeapYearForm);return;}
                    
                    if(y<0) {
                        showError("Only positive years",LeapYearForm);return;}
                    
                } catch(Exception e) {
                    return;}
                
                
                if((y%4 == 0  &&  y%100 != 0) || ( y%400 == 0)) {
                    if(y>0)
                        showInfoPause(lyearTF.getString()+" IS a leap year.",LeapYearForm);
                } else {
                    if(y>0)
                        showInfoPause(lyearTF.getString()+" is NOT a leap year.",LeapYearForm);
                }
            }//GEN-BEGIN:MVDCACase43
        } else if (displayable == DateForm2) {
            if (command == helpCommand1) {//GEN-END:MVDCACase43
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction39
                HandleCmd(command,displayable);
            } else if (command == okCommand1) {//GEN-LINE:MVDCACase39
                // Insert pre-action code here
                // Do nothing//GEN-LINE:MVDCAAction42
                HandleCmd(command,displayable);
            } else if (command == backCommand1) {//GEN-LINE:MVDCACase42
                // Insert pre-action code here
                getDisplay().setCurrent(get_DateForm1());//GEN-LINE:MVDCAAction38
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase38
        } else if (displayable == ResultForm) {
            if (command == doneCommand) {//GEN-END:MVDCACase38
                 if(screenAfterResult!=null)
                 {
                    Display.getDisplay(this).setCurrent(screenAfterResult);
                    screenAfterResult = null;
                 }
                else
                    Display.getDisplay(this).setCurrent(get_Menu());
                // Do nothing//GEN-LINE:MVDCAAction115
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase115
        }//GEN-END:MVDCACase115
    // Insert global post-action code here
}//GEN-LINE:MVDCAEnd

    /** This method returns instance for ticker1 component and should be called instead of accessing ticker1 field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for ticker1 component
     */
    public Ticker get_ticker1() {
        if (ticker1 == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            ticker1 = new Ticker("");//GEN-LINE:MVDGetInit2
            ticker1.setString(GetStringForTicker());
        }//GEN-BEGIN:MVDGetEnd2
        return ticker1;
    }//GEN-END:MVDGetEnd2

    /** This method returns instance for theAlert component and should be called instead of accessing theAlert field directly.//GEN-BEGIN:MVDGetBegin3
     * @return Instance for theAlert component
     */
    public Alert get_theAlert() {
        if (theAlert == null) {//GEN-END:MVDGetBegin3
            // Insert pre-init code here
            theAlert = new Alert(null, "<Enter Text>", null, AlertType.INFO);//GEN-BEGIN:MVDGetInit3
            theAlert.setTimeout(-2);//GEN-END:MVDGetInit3
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd3
        return theAlert;
    }//GEN-END:MVDGetEnd3

    /** This method returns instance for Menu component and should be called instead of accessing Menu field directly.//GEN-BEGIN:MVDGetBegin4
     * @return Instance for Menu component
     */
    public List get_Menu() {
        if (Menu == null) {//GEN-END:MVDGetBegin4
            // Insert pre-init code here
            Menu = new List("DaysToDay", Choice.IMPLICIT, new String[] {//GEN-BEGIN:MVDGetInit4
                "Count Down/Up",
                "Count to Future",
                "Count to Past",
                "Between Dates",
                "Search Engine",
                "Next ...day of week",
                "Last ...day of week",
                "Date Plus Days",
                "Date Minus Days",
                "Leap Year Check",
                "Help",
                "About..."
            }, new Image[] {
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            });
            Menu.addCommand(get_exitCommand1());
            Menu.setCommandListener(this);
            Menu.setSelectedFlags(new boolean[] {
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            });//GEN-END:MVDGetInit4
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd4
        return Menu;
    }//GEN-END:MVDGetEnd4

    /** This method returns instance for helpCommand1 component and should be called instead of accessing helpCommand1 field directly.//GEN-BEGIN:MVDGetBegin6
     * @return Instance for helpCommand1 component
     */
    public Command get_helpCommand1() {
        if (helpCommand1 == null) {//GEN-END:MVDGetBegin6
            // Insert pre-init code here
            helpCommand1 = new Command("Help", Command.HELP, 3);//GEN-LINE:MVDGetInit6
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd6
        return helpCommand1;
    }//GEN-END:MVDGetEnd6

    /** This method returns instance for exitCommand1 component and should be called instead of accessing exitCommand1 field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for exitCommand1 component
     */
    public Command get_exitCommand1() {
        if (exitCommand1 == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            exitCommand1 = new Command("Exit", Command.EXIT, 4);//GEN-LINE:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return exitCommand1;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for DateForm1 component and should be called instead of accessing DateForm1 field directly.//GEN-BEGIN:MVDGetBegin31
     * @return Instance for DateForm1 component
     */
    public Form get_DateForm1() {
        if (DateForm1 == null) {//GEN-END:MVDGetBegin31
            // Insert pre-init code here
            DateForm1 = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit31
                get_textField4(),
                get_textField5(),
                get_textField6()
            });
            DateForm1.addCommand(get_okCommand1());
            DateForm1.addCommand(get_backCommand1());
            DateForm1.addCommand(get_helpCommand1());
            DateForm1.setCommandListener(this);//GEN-END:MVDGetInit31
            initDateForm(DateForm1);
        }//GEN-BEGIN:MVDGetEnd31
        return DateForm1;
    }//GEN-END:MVDGetEnd31

    /** This method returns instance for DateForm2 component and should be called instead of accessing DateForm2 field directly.//GEN-BEGIN:MVDGetBegin32
     * @return Instance for DateForm2 component
     */
    public Form get_DateForm2() {
        if (DateForm2 == null) {//GEN-END:MVDGetBegin32
            // Insert pre-init code here
            DateForm2 = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit32
                get_textField7(),
                get_textField8(),
                get_textField9()
            });
            DateForm2.addCommand(get_okCommand1());
            DateForm2.addCommand(get_backCommand1());
            DateForm2.addCommand(get_helpCommand1());
            DateForm2.setCommandListener(this);//GEN-END:MVDGetInit32
            initDateForm(DateForm2);
        }//GEN-BEGIN:MVDGetEnd32
        return DateForm2;
    }//GEN-END:MVDGetEnd32

    /** This method returns instance for LeapYearForm component and should be called instead of accessing LeapYearForm field directly.//GEN-BEGIN:MVDGetBegin34
     * @return Instance for LeapYearForm component
     */
    public Form get_LeapYearForm() {
        if (LeapYearForm == null) {//GEN-END:MVDGetBegin34
            // Insert pre-init code here
            LeapYearForm = new Form("Leap Year Check", new Item[] {get_lyearTF()});//GEN-BEGIN:MVDGetInit34
            LeapYearForm.addCommand(get_okCommand1());
            LeapYearForm.addCommand(get_backCommand1());
            LeapYearForm.addCommand(get_helpCommand1());
            LeapYearForm.setCommandListener(this);//GEN-END:MVDGetInit34
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd34
        return LeapYearForm;
    }//GEN-END:MVDGetEnd34

    /** This method returns instance for backCommand1 component and should be called instead of accessing backCommand1 field directly.//GEN-BEGIN:MVDGetBegin35
     * @return Instance for backCommand1 component
     */
    public Command get_backCommand1() {
        if (backCommand1 == null) {//GEN-END:MVDGetBegin35
            // Insert pre-init code here
            backCommand1 = new Command("Back", Command.BACK, 2);//GEN-LINE:MVDGetInit35
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd35
        return backCommand1;
    }//GEN-END:MVDGetEnd35

    /** This method returns instance for okCommand1 component and should be called instead of accessing okCommand1 field directly.//GEN-BEGIN:MVDGetBegin40
     * @return Instance for okCommand1 component
     */
    public Command get_okCommand1() {
        if (okCommand1 == null) {//GEN-END:MVDGetBegin40
            // Insert pre-init code here
            okCommand1 = new Command("OK", Command.OK, 1);//GEN-LINE:MVDGetInit40
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd40
        return okCommand1;
    }//GEN-END:MVDGetEnd40

    /** This method returns instance for selectCommand1 component and should be called instead of accessing selectCommand1 field directly.//GEN-BEGIN:MVDGetBegin46
     * @return Instance for selectCommand1 component
     */
    public Command get_selectCommand1() {
        if (selectCommand1 == null) {//GEN-END:MVDGetBegin46
            if(false){
                selectCommand1 = new Command("Select", Command.OK, 1);//GEN-LINE:MVDGetInit46
            }selectCommand1 = List.SELECT_COMMAND;
        }//GEN-BEGIN:MVDGetEnd46
        return selectCommand1;
    }//GEN-END:MVDGetEnd46

    /** This method returns instance for TwoNumberForm component and should be called instead of accessing TwoNumberForm field directly.//GEN-BEGIN:MVDGetBegin47
     * @return Instance for TwoNumberForm component
     */
    public Form get_TwoNumberForm() {
        if (TwoNumberForm == null) {//GEN-END:MVDGetBegin47
            // Insert pre-init code here
            TwoNumberForm = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit47
                get_textField2(),
                get_textField3()
            });
            TwoNumberForm.addCommand(get_okCommand1());
            TwoNumberForm.addCommand(get_backCommand1());
            TwoNumberForm.addCommand(get_helpCommand1());
            TwoNumberForm.setCommandListener(this);//GEN-END:MVDGetInit47
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd47
        return TwoNumberForm;
    }//GEN-END:MVDGetEnd47

    /** This method returns instance for lyearTF component and should be called instead of accessing lyearTF field directly.//GEN-BEGIN:MVDGetBegin48
     * @return Instance for lyearTF component
     */
    public TextField get_lyearTF() {
        if (lyearTF == null) {//GEN-END:MVDGetBegin48
            // Insert pre-init code here
            lyearTF = new TextField("Year:", null, 5, TextField.NUMERIC);//GEN-LINE:MVDGetInit48
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd48
        return lyearTF;
    }//GEN-END:MVDGetEnd48

    /** This method returns instance for textField2 component and should be called instead of accessing textField2 field directly.//GEN-BEGIN:MVDGetBegin49
     * @return Instance for textField2 component
     */
    public TextField get_textField2() {
        if (textField2 == null) {//GEN-END:MVDGetBegin49
            // Insert pre-init code here
            textField2 = new TextField("", null, 9, TextField.NUMERIC);//GEN-LINE:MVDGetInit49
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd49
        return textField2;
    }//GEN-END:MVDGetEnd49

    /** This method returns instance for textField3 component and should be called instead of accessing textField3 field directly.//GEN-BEGIN:MVDGetBegin50
     * @return Instance for textField3 component
     */
    public TextField get_textField3() {
        if (textField3 == null) {//GEN-END:MVDGetBegin50
            // Insert pre-init code here
            textField3 = new TextField("", "", 9, TextField.NUMERIC);//GEN-LINE:MVDGetInit50
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd50
        return textField3;
    }//GEN-END:MVDGetEnd50

    /** This method returns instance for textField4 component and should be called instead of accessing textField4 field directly.//GEN-BEGIN:MVDGetBegin56
     * @return Instance for textField4 component
     */
    public TextField get_textField4() {
        if (textField4 == null) {//GEN-END:MVDGetBegin56
            // Insert pre-init code here
            textField4 = new TextField("Day", null, 2, TextField.NUMERIC);//GEN-LINE:MVDGetInit56
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd56
        return textField4;
    }//GEN-END:MVDGetEnd56

    /** This method returns instance for textField5 component and should be called instead of accessing textField5 field directly.//GEN-BEGIN:MVDGetBegin57
     * @return Instance for textField5 component
     */
    public TextField get_textField5() {
        if (textField5 == null) {//GEN-END:MVDGetBegin57
            // Insert pre-init code here
            textField5 = new TextField("Month", "", 2, TextField.NUMERIC);//GEN-LINE:MVDGetInit57
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd57
        return textField5;
    }//GEN-END:MVDGetEnd57

    /** This method returns instance for textField6 component and should be called instead of accessing textField6 field directly.//GEN-BEGIN:MVDGetBegin58
     * @return Instance for textField6 component
     */
    public TextField get_textField6() {
        if (textField6 == null) {//GEN-END:MVDGetBegin58
            // Insert pre-init code here
            textField6 = new TextField("Year", null, 5, TextField.NUMERIC);//GEN-LINE:MVDGetInit58
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd58
        return textField6;
    }//GEN-END:MVDGetEnd58

    /** This method returns instance for textField7 component and should be called instead of accessing textField7 field directly.//GEN-BEGIN:MVDGetBegin59
     * @return Instance for textField7 component
     */
    public TextField get_textField7() {
        if (textField7 == null) {//GEN-END:MVDGetBegin59
            // Insert pre-init code here
            textField7 = new TextField("Day", null, 2, TextField.NUMERIC);//GEN-LINE:MVDGetInit59
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd59
        return textField7;
    }//GEN-END:MVDGetEnd59

    /** This method returns instance for textField8 component and should be called instead of accessing textField8 field directly.//GEN-BEGIN:MVDGetBegin60
     * @return Instance for textField8 component
     */
    public TextField get_textField8() {
        if (textField8 == null) {//GEN-END:MVDGetBegin60
            // Insert pre-init code here
            textField8 = new TextField("Month", null, 2, TextField.NUMERIC);//GEN-LINE:MVDGetInit60
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd60
        return textField8;
    }//GEN-END:MVDGetEnd60

    /** This method returns instance for textField9 component and should be called instead of accessing textField9 field directly.//GEN-BEGIN:MVDGetBegin61
     * @return Instance for textField9 component
     */
    public TextField get_textField9() {
        if (textField9 == null) {//GEN-END:MVDGetBegin61
            // Insert pre-init code here
            textField9 = new TextField("Year", "", 5, TextField.NUMERIC);//GEN-LINE:MVDGetInit61
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd61
        return textField9;
    }//GEN-END:MVDGetEnd61

    /** This method returns instance for textField10 component and should be called instead of accessing textField10 field directly.//GEN-BEGIN:MVDGetBegin70
     * @return Instance for textField10 component
     */
    public TextField get_textField10() {
        if (textField10 == null) {//GEN-END:MVDGetBegin70
            // Insert pre-init code here
            textField10 = new TextField("Day (empty if not specified)", "", 120, TextField.ANY);//GEN-LINE:MVDGetInit70
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd70
        return textField10;
    }//GEN-END:MVDGetEnd70

    /** This method returns instance for textField11 component and should be called instead of accessing textField11 field directly.//GEN-BEGIN:MVDGetBegin71
     * @return Instance for textField11 component
     */
    public TextField get_textField11() {
        if (textField11 == null) {//GEN-END:MVDGetBegin71
            // Insert pre-init code here
            textField11 = new TextField("textField11", null, 120, TextField.ANY);//GEN-LINE:MVDGetInit71
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd71
        return textField11;
    }//GEN-END:MVDGetEnd71

    /** This method returns instance for textField12 component and should be called instead of accessing textField12 field directly.//GEN-BEGIN:MVDGetBegin72
     * @return Instance for textField12 component
     */
    public TextField get_textField12() {
        if (textField12 == null) {//GEN-END:MVDGetBegin72
            // Insert pre-init code here
            textField12 = new TextField("textField12", null, 120, TextField.ANY);//GEN-LINE:MVDGetInit72
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd72
        return textField12;
    }//GEN-END:MVDGetEnd72

    /** This method returns instance for SearchEngineForm component and should be called instead of accessing SearchEngineForm field directly.//GEN-BEGIN:MVDGetBegin73
     * @return Instance for SearchEngineForm component
     */
    public Form get_SearchEngineForm() {
        if (SearchEngineForm == null) {//GEN-END:MVDGetBegin73
            // Insert pre-init code here
            SearchEngineForm = new Form("Date SearchEngine", new Item[] {//GEN-BEGIN:MVDGetInit73
                get_stringItem1(),
                get_dowCG(),
                get_stringItem2(),
                get_dCG(),
                get_mCG(),
                get_yTF(),
                get_stringItem3(),
                get_rCG()
            });
            SearchEngineForm.addCommand(get_okCommand1());
            SearchEngineForm.addCommand(get_backCommand1());
            SearchEngineForm.addCommand(get_helpCommand1());
            SearchEngineForm.setCommandListener(this);//GEN-END:MVDGetInit73
            SearchEngineForm.setItemStateListener(this);
        }//GEN-BEGIN:MVDGetEnd73
        return SearchEngineForm;
    }//GEN-END:MVDGetEnd73

    /** This method returns instance for stringItem1 component and should be called instead of accessing stringItem1 field directly.//GEN-BEGIN:MVDGetBegin74
     * @return Instance for stringItem1 component
     */
    public StringItem get_stringItem1() {
        if (stringItem1 == null) {//GEN-END:MVDGetBegin74
            // Insert pre-init code here
            stringItem1 = new StringItem("Step 1", "Please choose a day-of-week.");//GEN-LINE:MVDGetInit74
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd74
        return stringItem1;
    }//GEN-END:MVDGetEnd74

    /** This method returns instance for dowCG component and should be called instead of accessing dowCG field directly.//GEN-BEGIN:MVDGetBegin75
     * @return Instance for dowCG component
     */
    public ChoiceGroup get_dowCG() {
        if (dowCG == null) {//GEN-END:MVDGetBegin75
            // Insert pre-init code here
            dowCG = new ChoiceGroup("Day-Of-Week", Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit75
                "Any",
                "Choose"
            }, new Image[] {
                null,
                null
            });
            dowCG.setSelectedFlags(new boolean[] {
                false,
                false
            });//GEN-END:MVDGetInit75
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd75
        return dowCG;
    }//GEN-END:MVDGetEnd75

    /** This method returns instance for stringItem2 component and should be called instead of accessing stringItem2 field directly.//GEN-BEGIN:MVDGetBegin78
     * @return Instance for stringItem2 component
     */
    public StringItem get_stringItem2() {
        if (stringItem2 == null) {//GEN-END:MVDGetBegin78
            // Insert pre-init code here
            stringItem2 = new StringItem("Step 2", "Please enter a date template/pattern to search for.");//GEN-LINE:MVDGetInit78
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd78
        return stringItem2;
    }//GEN-END:MVDGetEnd78

    /** This method returns instance for dCG component and should be called instead of accessing dCG field directly.//GEN-BEGIN:MVDGetBegin79
     * @return Instance for dCG component
     */
    public ChoiceGroup get_dCG() {
        if (dCG == null) {//GEN-END:MVDGetBegin79
            // Insert pre-init code here
            dCG = new ChoiceGroup("Day of Month:", Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit79
                "Any day of month",
                "Specify"
            }, new Image[] {
                null,
                null
            });
            dCG.setSelectedFlags(new boolean[] {
                false,
                false
            });//GEN-END:MVDGetInit79
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd79
        return dCG;
    }//GEN-END:MVDGetEnd79

    /** This method returns instance for stringItem3 component and should be called instead of accessing stringItem3 field directly.//GEN-BEGIN:MVDGetBegin82
     * @return Instance for stringItem3 component
     */
    public StringItem get_stringItem3() {
        if (stringItem3 == null) {//GEN-END:MVDGetBegin82
            // Insert pre-init code here
            stringItem3 = new StringItem("Step 3", "Please specify the range for this search relevant to the specified year above.");//GEN-LINE:MVDGetInit82
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd82
        return stringItem3;
    }//GEN-END:MVDGetEnd82

    /** This method returns instance for rCG component and should be called instead of accessing rCG field directly.//GEN-BEGIN:MVDGetBegin83
     * @return Instance for rCG component
     */
    public ChoiceGroup get_rCG() {
        if (rCG == null) {//GEN-END:MVDGetBegin83
            // Insert pre-init code here
            rCG = new ChoiceGroup("Search Range:", Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit83
                "Same Year",
                "+5 Years",
                "+10 Years",
                "+50 Years",
                "+100 Years"
            }, new Image[] {
                null,
                null,
                null,
                null,
                null
            });
            rCG.setSelectedFlags(new boolean[] {
                false,
                false,
                false,
                false,
                false
            });//GEN-END:MVDGetInit83
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd83
        return rCG;
    }//GEN-END:MVDGetEnd83

    /** This method returns instance for choiceGroup4 component and should be called instead of accessing choiceGroup4 field directly.//GEN-BEGIN:MVDGetBegin85
     * @return Instance for choiceGroup4 component
     */
    public ChoiceGroup get_choiceGroup4() {
        if (choiceGroup4 == null) {//GEN-END:MVDGetBegin85
            // Insert pre-init code here
            choiceGroup4 = new ChoiceGroup("choiceGroup4", Choice.MULTIPLE, new String[0], new Image[0]);//GEN-BEGIN:MVDGetInit85
            choiceGroup4.setSelectedFlags(new boolean[0]);//GEN-END:MVDGetInit85
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd85
        return choiceGroup4;
    }//GEN-END:MVDGetEnd85

    /** This method returns instance for mCG component and should be called instead of accessing mCG field directly.//GEN-BEGIN:MVDGetBegin88
     * @return Instance for mCG component
     */
    public ChoiceGroup get_mCG() {
        if (mCG == null) {//GEN-END:MVDGetBegin88
            // Insert pre-init code here
            mCG = new ChoiceGroup("Month:", Choice.EXCLUSIVE, new String[] {//GEN-BEGIN:MVDGetInit88
                "Any Month",
                "Specify"
            }, new Image[] {
                null,
                null
            });
            mCG.setSelectedFlags(new boolean[] {
                false,
                false
            });//GEN-END:MVDGetInit88
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd88
        return mCG;
    }//GEN-END:MVDGetEnd88

    /** This method returns instance for yTF component and should be called instead of accessing yTF field directly.//GEN-BEGIN:MVDGetBegin98
     * @return Instance for yTF component
     */
    public TextField get_yTF() {
        if (yTF == null) {//GEN-END:MVDGetBegin98
            // Insert pre-init code here
            yTF = new TextField("Year:", null, 5, TextField.NUMERIC);//GEN-LINE:MVDGetInit98
            yTF.setString(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
        }//GEN-BEGIN:MVDGetEnd98
        return yTF;
    }//GEN-END:MVDGetEnd98

    /** This method returns instance for OneNumberForm component and should be called instead of accessing OneNumberForm field directly.//GEN-BEGIN:MVDGetBegin99
     * @return Instance for OneNumberForm component
     */
    public Form get_OneNumberForm() {
        if (OneNumberForm == null) {//GEN-END:MVDGetBegin99
            // Insert pre-init code here
            OneNumberForm = new Form(null, new Item[] {//GEN-BEGIN:MVDGetInit99
                get_stringItem4(),
                get_textField13()
            });
            OneNumberForm.addCommand(get_backCommand1());
            OneNumberForm.addCommand(get_helpCommand1());
            OneNumberForm.setCommandListener(this);//GEN-END:MVDGetInit99
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd99
        return OneNumberForm;
    }//GEN-END:MVDGetEnd99

    /** This method returns instance for textField13 component and should be called instead of accessing textField13 field directly.//GEN-BEGIN:MVDGetBegin100
     * @return Instance for textField13 component
     */
    public TextField get_textField13() {
        if (textField13 == null) {//GEN-END:MVDGetBegin100
            // Insert pre-init code here
            textField13 = new TextField("", null, 2, TextField.NUMERIC);//GEN-LINE:MVDGetInit100
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd100
        return textField13;
    }//GEN-END:MVDGetEnd100

    /** This method returns instance for stringItem4 component and should be called instead of accessing stringItem4 field directly.//GEN-BEGIN:MVDGetBegin101
     * @return Instance for stringItem4 component
     */
    public StringItem get_stringItem4() {
        if (stringItem4 == null) {//GEN-END:MVDGetBegin101
            // Insert pre-init code here
            stringItem4 = new StringItem("", "");//GEN-LINE:MVDGetInit101
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd101
        return stringItem4;
    }//GEN-END:MVDGetEnd101

    /** This method returns instance for ResultForm component and should be called instead of accessing ResultForm field directly.//GEN-BEGIN:MVDGetBegin108
     * @return Instance for ResultForm component
     */
    public Form get_ResultForm() {
        if (ResultForm == null) {//GEN-END:MVDGetBegin108
            // Insert pre-init code here
            ResultForm = new Form(null, new Item[] {get_resultSI()});//GEN-BEGIN:MVDGetInit108
            ResultForm.addCommand(get_doneCommand());
            ResultForm.setCommandListener(this);//GEN-END:MVDGetInit108
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd108
        return ResultForm;
    }//GEN-END:MVDGetEnd108

    /** This method returns instance for resultSI component and should be called instead of accessing resultSI field directly.//GEN-BEGIN:MVDGetBegin109
     * @return Instance for resultSI component
     */
    public StringItem get_resultSI() {
        if (resultSI == null) {//GEN-END:MVDGetBegin109
            // Insert pre-init code here
            resultSI = new StringItem("Result", "");//GEN-LINE:MVDGetInit109
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd109
        return resultSI;
    }//GEN-END:MVDGetEnd109

    /** This method returns instance for doneCommand component and should be called instead of accessing doneCommand field directly.//GEN-BEGIN:MVDGetBegin114
     * @return Instance for doneCommand component
     */
    public Command get_doneCommand() {
        if (doneCommand == null) {//GEN-END:MVDGetBegin114
            // Insert pre-init code here
            doneCommand = new Command("Done", Command.BACK, 1);//GEN-LINE:MVDGetInit114
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd114
        return doneCommand;
    }//GEN-END:MVDGetEnd114
    
    public void startApp() {
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    img = null;
    waitCanvas = null;
    }
    
    
      public void showError(String s, Displayable nextScreen)
    {
        
        get_theAlert().setTitle("Failed");
        get_theAlert().setString(s);      
        get_theAlert().setType(AlertType.ERROR);
        get_theAlert().setTimeout(2000);
        
        if(nextScreen!=null)
            Display.getDisplay(this).setCurrent(get_theAlert(),nextScreen);
        else
            Display.getDisplay(this).setCurrent(get_theAlert());
        System.gc();
    }
    
    public void showInfo(String s, Displayable nextScreen)
    {
        get_theAlert().setTitle("Info");
        get_theAlert().setString(s);            
        get_theAlert().setType(AlertType.INFO);
        get_theAlert().setTimeout(2000);
        
        
        if(nextScreen!=null)
            Display.getDisplay(this).setCurrent(get_theAlert(),nextScreen);
        else
            Display.getDisplay(this).setCurrent(get_theAlert());
        System.gc();
    }
    
    public void showIntro(String s)
    {
        
        get_resultSI().setLabel("Please Help");
        get_resultSI().setText(s);
        screenAfterResult = null;        
        Display.getDisplay(this).setCurrent(get_ResultForm());
    }
    
    public void showInfoPause(String s, Displayable nextScreen)
    {
        
        get_resultSI().setLabel("Result:");
        get_resultSI().setText(s);
        screenAfterResult = nextScreen;        
        Display.getDisplay(this).setCurrent(get_ResultForm());
        
        /*get_theAlert().setTitle("Info");
        get_theAlert().setString(s);            
        get_theAlert().setType(AlertType.INFO);
        get_theAlert().setTimeout(Alert.FOREVER);
        
        
        if(nextScreen!=null)
            Display.getDisplay(this).setCurrent(get_theAlert(),nextScreen);
        else
            Display.getDisplay(this).setCurrent(get_theAlert());
        */
        System.gc();
    }
    public void showSuccess(String s, Displayable nextScreen)
    {
        get_theAlert().setTitle("Success");
        get_theAlert().setString(s);            
        get_theAlert().setType(AlertType.INFO);
        get_theAlert().setTimeout(2000);
        
        
        if(nextScreen!=null)
            Display.getDisplay(this).setCurrent(get_theAlert(),nextScreen);
        else
            Display.getDisplay(this).setCurrent(get_theAlert());
        System.gc();
    }
    

      
    public void showHelp(String s)
    {
        get_resultSI().setLabel("Help:");
        get_resultSI().setText(s);
        screenAfterResult = Display.getDisplay(this).getCurrent();        
        Display.getDisplay(this).setCurrent(get_ResultForm());
        
        /*get_theAlert().setTitle("Help");
        get_theAlert().setString(s);            
        get_theAlert().setType(AlertType.INFO);
        get_theAlert().setTimeout(Alert.FOREVER);
        
        
        if(nextScreen!=null)
            Display.getDisplay(this).setCurrent(get_theAlert(),nextScreen);
        else
            Display.getDisplay(this).setCurrent(get_theAlert());
        */
        System.gc();
    }
    public void showAbout()
    {
        get_resultSI().setLabel("About:");
        get_resultSI().setText("DaysToDay Version 2.2 Copyright (c) 2007 Kasidit Yusuf. All rights reserved. This software is FREE! Please help the poor, the orphans and spend in charity! Please do good and stay away from bad deeds! May God Guide & Bless. \nDISCLAIMER: This application is provided AS IS. No warranties whatsoever are implied.\nNOTE: This program is compatible with the Gregorian calendar only.");
        screenAfterResult = get_Menu();        
        Display.getDisplay(this).setCurrent(get_ResultForm());
        
        /*get_theAlert().setTitle("About");
        get_theAlert().setString("DaysToDay Version 2.1 Copyright (c) 2007 Kasidit Yusuf. All rights reserved. This software is FREE! Please help the poor, the orphans and spend in charity! Please do good and stay away from bad deeds! May God Guide & Bless. \nDISCLAIMER: This application is provided AS IS. No warranties whatsoever are implied.\nNOTE: This program is compatible with the Gregorian calendar only.");            
        get_theAlert().setType(AlertType.INFO);
        get_theAlert().setTimeout(Alert.FOREVER);   
        Display.getDisplay(this).setCurrent(get_theAlert(),get_Menu());  */
        System.gc();
    }
    
    
    public void CountDown()
    {
        get_DateForm1().setTitle("Date to Count Down/Up");
        Display.getDisplay(this).setCurrent(get_DateForm1());
        System.gc();
    }    
    public void DoCountDown()
    {
        int[] dmy;
        try
        {
            dmy = this.getDateFormDate(this.get_DateForm1());        
        }
        catch(Exception e)
        {System.gc();return;}
        
        
        Calendar calendar2 = Calendar.getInstance();
        long dbtw = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),dmy[0],dmy[1],dmy[2]);
        if(past(dmy[0],dmy[1],dmy[2]))
        {
                        
                        
                        String tmpS = "There are "+ dbtw + " day(s) from ";                        
                        tmpS += getFullDate(dmy[0],dmy[1],dmy[2],dbtw)+" to Today.";                        
                        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_DateForm1());
                        tmpS = null;
                        calendar2=null;
                        dmy=null;
        }
        else
        {
              String tmpS = "There are "+ dbtw + " day(s) remaining until ";                        
              tmpS += getFullDate(dmy[0],dmy[1],dmy[2],dbtw)+".";                        
                        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_DateForm1());
                        tmpS = null;
                        calendar2=null;
                        dmy=null;
        
        
        }    
        System.gc();
        
    }
    public void CountToFuture ()
    {
        get_TwoNumberForm().setTitle("Count To Future");
        ((TextField)get_TwoNumberForm().get(0)).setLabel("Add Days");
        ((TextField)get_TwoNumberForm().get(1)).setLabel("Add Weeks");
        Display.getDisplay(this).setCurrent(get_TwoNumberForm());        
        System.gc();
    }
    public void DoCountToFuture()
    {
        long[] dw;
        long dbtw;
        try{
        dw = getDaysWeeksFromForm(get_TwoNumberForm());
        dbtw = (dw[0]+(dw[1]*7));
        }catch(Exception e){return;}
        Calendar c = Calendar.getInstance();        
        int[] dmy = daysPlusDate(dbtw,c.get(c.DAY_OF_MONTH),c.get(c.MONTH)+1,c.get(c.YEAR));
        String tmpS = this.getFullDate(dmy[0], dmy[1],dmy[2], dbtw)+" is "+dbtw+" day(s) after Today.";
        
        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_TwoNumberForm());
                        tmpS = null;
                        c=null;
                        dmy=null;
                        dw=null;
        System.gc();
        
    }
    public void CountToPast()
    {
        get_TwoNumberForm().setTitle("Count To Past");
        ((TextField)get_TwoNumberForm().get(0)).setLabel("Remove Days");
        ((TextField)get_TwoNumberForm().get(1)).setLabel("Remove Weeks");
        Display.getDisplay(this).setCurrent(get_TwoNumberForm());
        System.gc();
    }
    public void DoCountToPast()
    {
    
        long[] dw;
        long dbtw;
        try{
        dw = getDaysWeeksFromForm(get_TwoNumberForm());
        dbtw = (dw[0]+(dw[1]*7));
        }catch(Exception e){return;}
        Calendar c = Calendar.getInstance();        
        int[] dmy = this.daysMinusDate(dbtw,c.get(c.DAY_OF_MONTH),c.get(c.MONTH)+1,c.get(c.YEAR));
        String tmpS = this.getFullDate(dmy[0], dmy[1],dmy[2], dbtw)+" is "+dbtw+" day(s) before Today.";
        
        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_TwoNumberForm());
                        tmpS = null;
                        c=null;
                        dmy=null;
                        dw=null;
     System.gc();
    }
    public void BetweenDates()
    {
        get_DateForm1().setTitle("Between: First Date");
        Display.getDisplay(this).setCurrent(get_DateForm1());
        System.gc();
    }
    public void DoBetweenDates()
    {
        int[] fdmy,sdmy;
        try
        {
            fdmy = this.getDateFormDate(this.get_DateForm1());
            sdmy = this.getDateFormDate(this.get_DateForm2());
        }
        catch(Exception e)
        {return;}           
        Calendar calendar2 = Calendar.getInstance();
        long fbtw = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),fdmy[0],fdmy[1],fdmy[2]);        
        long sbtw = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),sdmy[0],sdmy[1],sdmy[2]);        
        long dbtw = dtd(fdmy[0],fdmy[1],fdmy[2],sdmy[0],sdmy[1],sdmy[2]);
        
        
        
        
        String tmpS = "There are "+ dbtw + " day(s) from ";                        
        tmpS += getFullDate(fdmy[0],fdmy[1],fdmy[2],fbtw)+" and "+getFullDate(sdmy[0],sdmy[1],sdmy[2],sbtw);                        
                    if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_DateForm2());
                        tmpS = null;
                        calendar2=null;
                        fdmy=null;
                        sdmy=null;
           System.gc();
    }
    public void NextDOW()
    {
        DOWList.setTitle("Next:");
        Display.getDisplay(this).setCurrent(DOWList);
        System.gc();
    }
    public void DoNextDOW()
    {
        
        getAndShowNextDOW(DOWList.getSelectedIndex()+1);    
        System.gc();
    }
    
    public void LastDOW()
    {
        DOWList.setTitle("Last:");
        Display.getDisplay(this).setCurrent(DOWList);
        System.gc();
    }
    public void DoLastDOW()
    {
        getAndShowLastDOW(DOWList.getSelectedIndex()+1);    
        System.gc();
    }
    
    
    public void DPD()
    {
        get_DateForm1().setTitle("Step 1: Start Date");
        Display.getDisplay(this).setCurrent(get_DateForm1());  
        System.gc();
    }
    public void DoDPD()
    {
        int[] dmy;
        try
        {
            dmy = this.getDateFormDate(this.get_DateForm1());        
        }
        catch(Exception e)
        {System.gc();return;}
        long[] dw;
        long dbtw;
        try{
        dw = getDaysWeeksFromForm(get_TwoNumberForm());
        dbtw = (dw[0]+(dw[1]*7));
        }catch(Exception e){return;}        
        int[] resdmy = daysPlusDate(dbtw,dmy[0],dmy[1],dmy[2]);
        Calendar calendar2 = Calendar.getInstance();
        long resfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),resdmy[0],resdmy[1],resdmy[2]);        
        long startfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),dmy[0],dmy[1],dmy[2]);        
        
        
        String tmpS = this.getFullDate(resdmy[0], resdmy[1],resdmy[2], resfromtoday)+" is "+dbtw+" day(s) after "+this.getFullDate(dmy[0], dmy[1],dmy[2], startfromtoday)+".";
        
        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_TwoNumberForm());
                        tmpS = null;
                        calendar2=null;
                        dmy=null;
                        dw=null;
       System.gc();
        
        
    }
    public void DMD()
    {
        get_DateForm1().setTitle("Step 1: Start Date");
        Display.getDisplay(this).setCurrent(get_DateForm1());
        System.gc();
    }
    public void DoDMD()
    {
        int[] dmy;
        try
        {
            dmy = this.getDateFormDate(this.get_DateForm1());        
        }
        catch(Exception e)
        {return;}
        long[] dw;
        long dbtw;
        try{
        dw = getDaysWeeksFromForm(get_TwoNumberForm());
        dbtw = (dw[0]+(dw[1]*7));
        }catch(Exception e){return;}        
        int[] resdmy = daysMinusDate(dbtw,dmy[0],dmy[1],dmy[2]);
        Calendar calendar2 = Calendar.getInstance();
        long resfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),resdmy[0],resdmy[1],resdmy[2]);        
        long startfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),dmy[0],dmy[1],dmy[2]);        
        
        
        String tmpS = this.getFullDate(resdmy[0], resdmy[1],resdmy[2], resfromtoday)+" is "+dbtw+" day(s) before "+this.getFullDate(dmy[0], dmy[1],dmy[2], startfromtoday)+".";
        
        if(dbtw/7 > 0)        
                        {
                            tmpS += " (";
                            tmpS += dbtw/7 + " week(s)";
                            if(dbtw%7>0)
                            {
                                tmpS+=" and "+dbtw%7+" day(s)";
                            }
                                tmpS += ")";                                   
                        }
                        showInfoPause(tmpS, get_TwoNumberForm());
                        tmpS = null;
                        calendar2=null;
                        dmy=null;
                        dw=null;
       System.gc();
    }
    
    public void DateSearchEngine()
    {
        
        Display.getDisplay(this).setCurrent(get_SearchEngineForm());    
        
    }
    public void LeapYearCheck()
    {
        get_OneNumberForm().setTitle("Leap Year Check");
        ((TextField)get_OneNumberForm().get(0)).setLabel("Year");        
        Display.getDisplay(this).setCurrent(get_OneNumberForm());
    
    }    
    public void HandleCmd(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable)
    {
           
        
        switch(Menu.getSelectedIndex())
           {
               case COUNTDOWNINDEX:              
               {
                    if(command == this.get_helpCommand1())
                    {
                        showHelp("Please enter a date to make a Count Down or Count Up. DaysToDay would tell you how many days/weeks are remaining until then if it is a future date, or haw many days/weeks passed since that date if it is a past date. DaysToDay would also tells you the day-of-week of the entered date.");
                    }
                    else
                    if(command == this.get_okCommand1())
                    {
                        DoCountDown();                        
                    }
                     
               }
                    break;  
               case COUNTTOFUTUREINDEX:             
               {
               
                    if(command == this.get_helpCommand1())
                    {
                        showHelp("Please enter the number of days and/or weeks to count forward after Today.");
                    }
                    else
                    if(command == this.get_okCommand1())
                    {
                        DoCountToFuture();                        
                    }
                     
               }
                   break;
               case COUNTTOPASTINDEX:                   
               {
                   if(command == this.get_helpCommand1())
                    {
                        showHelp("Please enter the number of days and/or weeks to count backward before Today.");
                    }
                    else
                    if(command == this.get_okCommand1())
                    {
                        DoCountToPast();                        
                    }               
               }
                   break;
               case BETWEENDATESINDEX:
               {
                    if(displayable==DateForm1)
                    {
                        if(command == get_okCommand1())
                        {
                            try
                            {
                                this.getDateFormDate(DateForm1);
                            }
                            catch(Exception e){return;}
                            get_DateForm2().setTitle("Between: Second Date");
                            this.showInfo("Now, enter the second date.", get_DateForm2());                            
                        }                       
                        else
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the first of the two dates to count the number of days between them.");                        
                        }
                    }
                    else
                    if(displayable == DateForm2)
                    { 
                        if(command == get_okCommand1())
                        {                        
                            try
                            {
                                this.getDateFormDate(DateForm2);
                            }
                            catch(Exception e){return;}
                            DoBetweenDates();
                        }
                        else                        
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the second of the two dates to count the number of days between them.");                        
                        }               
                    }
                   
               }
                   break;                   
               case NEXTDOWINDEX:
               {
                    if(command == this.get_helpCommand1())
                    {
                        showHelp("Please select a day to know its next occurrence.");
                    }
                    else
                    if(command == this.get_selectCommand1())
                    {
                        DoNextDOW();                        
                    }
                    if(command == this.get_backCommand1())
                    {
                        Display.getDisplay(this).setCurrent(get_Menu());               
                    }
               }
                   break;
               case LASTDOWINDEX:
               {
                    if(command == this.get_helpCommand1())
                    {
                        showHelp("Please select a day to know its previous occurrence.");
                    }
                    else
                    if(command == this.get_selectCommand1())
                    {
                        DoLastDOW();                        
                    }
                    if(command == this.get_backCommand1())
                    {
                        Display.getDisplay(this).setCurrent(get_Menu());               
                    }
               }
                   break;
               case DPDINDEX:
               {
                     if(displayable==DateForm1)
                    {
                        if(command == get_okCommand1())
                        {
                            try
                            {
                                this.getDateFormDate(DateForm1);
                            }
                            catch(Exception e){return;}
                            get_TwoNumberForm().setTitle("Step 2: Add");
                            ((TextField)get_TwoNumberForm().get(0)).setLabel("Add Days");
                            ((TextField)get_TwoNumberForm().get(1)).setLabel("Add Weeks");
                            this.showInfo("Now, enter how many to add.", get_TwoNumberForm());                            
                        }                       
                        else
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the start date to do the counting.");                        
                        }
                    }
                    else
                    if(displayable == TwoNumberForm)
                    { 
                        if(command == get_okCommand1())
                        {                        
                            try
                            {
                                this.getDaysWeeksFromForm(TwoNumberForm);
                            }
                            catch(Exception e){return;}
                            DoDPD();
                        }
                        else                        
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the number of days and/or weeks to count after the starting date in the previous screen.");                        
                        }               
                    }
               }
                   break;
               case DMDINDEX:
               {
                     if(displayable==DateForm1)
                    {
                        if(command == get_okCommand1())
                        {
                            try
                            {
                                this.getDateFormDate(DateForm1);
                            }
                            catch(Exception e){return;}
                            get_TwoNumberForm().setTitle("Step 2: Remove");
                            ((TextField)get_TwoNumberForm().get(0)).setLabel("Remove Days:");
                            ((TextField)get_TwoNumberForm().get(1)).setLabel("Remove Weeks:");
                            this.showInfo("Now, enter how many to remove.", get_TwoNumberForm());                            
                        }                       
                        else
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the start date to do the counting.");                        
                        }
                    }
                    else
                    if(displayable == TwoNumberForm)
                    { 
                        if(command == get_okCommand1())
                        {                        
                            try
                            {
                                this.getDaysWeeksFromForm(TwoNumberForm);
                            }
                            catch(Exception e){return;}
                            DoDMD();
                        }
                        else                        
                        if(command == get_helpCommand1())
                        {
                            showHelp("Please enter the number of days and/or weeks to count before the starting date in the previous screen.");                        
                        }               
                    }                   
               }
                   break;
               case DATESEARCHENGINEINDEX:
               {
                              
                   if(displayable == DOWList)
                   {
                        if(command==backCommand1 || command== List.SELECT_COMMAND)
                        {
                            Display.getDisplay(this).setCurrent(SearchEngineForm);
                            dowCG.set(0, DOWList.getString(DOWList.getSelectedIndex()), null);
                            DOWList.delete(0);
                        }else
                        if(command == this.get_helpCommand1())
                        {
                            showHelp("Please select a day of week to specify, then press \"Back\".");
                        }
                    
                   }                  
                   else                   
                   if(displayable == OneNumberForm)
                   {
                        if(dCG.getSelectedIndex()==1)
                        {
                            if(command == backCommand1)
                            {
                                String str = null;
                                str = ((TextField)get_OneNumberForm().get(1)).getString();
                                try
                                {
                                    int i = Integer.parseInt(str);
                                    if(i<0 || i>31)
                                    {
                                        ((TextField)get_OneNumberForm().get(1)).setString(null);
                                        showError("The day entered is not a valid day of month", OneNumberForm);return;                                    
                                    }
                                    
                                }
                                catch(Exception e){}                                
                                
                                if(str==null || str.trim().equals("") || str.trim().equals("0"))
                                        dCG.set(0, "Any day of month",null);
                                    else
                                        dCG.set(0, str,null);
                                
                                dCG.setSelectedIndex(0, true);            
                                Display.getDisplay(this).setCurrent(SearchEngineForm);
                            }
                            else
                            if(command == helpCommand1)
                            {
                              this.showHelp("Please enter the desired day of month to be used in your search query, then press \"Back\".");
                            }
                            //dowCG.setSelectedIndex(0, true);            
                        }
                        else
                        if(mCG.getSelectedIndex()==1)
                        {
                             if(command == backCommand1)
                            {
                                String str = null;
                                str = ((TextField)get_OneNumberForm().get(1)).getString();
                                
                                    try
                                {
                                    int i = Integer.parseInt(str);
                                    if(i<0 || i>12)
                                    {
                                        ((TextField)get_OneNumberForm().get(1)).setString(null);
                                        showError("The month entered is not valid.", OneNumberForm);return;
                                    }
                                    
                                }
                                catch(Exception e){}                                
                                    
                                    if(str==null || str.trim().equals("") || str.trim().equals("0"))
                                        mCG.set(0, "Any month",null);
                                    else
                                        mCG.set(0, str, null);
                                
                                mCG.setSelectedIndex(0, true);            
                                Display.getDisplay(this).setCurrent(SearchEngineForm);
                            }
                            else
                            if(command == helpCommand1)
                            {
                              this.showHelp("Please enter the desired month to be used in your search query, then press \"Back\".");
                            }
                        }
                   
                   }                   
                   else                  
                  if(displayable == SearchEngineForm)
                  {
                    
                       
                       DOWList.setTitle("Day of Week:");
                       
                       
                       
                       if(command == okCommand1)
                    {
                        
                        Display.getDisplay(this).setCurrent(waitCanvas);
                        waitCanvas.repaint();
                        waitCanvas.serviceRepaints();
                        
                        
                        
                        //check yTF   year
                        try
                        {
                            y = Integer.parseInt(yTF.getString());                            
                            if(y<=0)
                            {
                                showError("Please specify a valid year", SearchEngineForm);return;                            
                            }
                        }
                        catch(Exception e)
                        {
                            showError("Please specify a valid year", SearchEngineForm);return;                            
                        }
                        
                        
                            if(dowCG.getString(0).startsWith("Any"))
                                dow=-1;
                            else
                            if(dowCG.getString(0).startsWith("Sun"))
                                dow=1;
                            else
                            if(dowCG.getString(0).startsWith("Mon"))
                                dow=2;
                            else
                            if(dowCG.getString(0).startsWith("Tue"))
                                dow=3;
                            else
                            if(dowCG.getString(0).startsWith("Wed"))
                                dow=4;
                            else
                            if(dowCG.getString(0).startsWith("Thu"))
                                dow=5;
                            else
                            if(dowCG.getString(0).startsWith("Fri"))
                                dow=6;
                            else
                            if(dowCG.getString(0).startsWith("Sat"))
                                dow=7;
                            
                        
                        try{
                            
                            if(dCG.getString(0).startsWith("Any"))
                                d = 0;
                            else
                                d = Integer.parseInt(dCG.getString(0));
                        }
                        catch(Exception e)
                        {
                             showError("Please specify a valid day of month", SearchEngineForm);return;                            
                        }
                        
                        
                        try{
                            
                            if(mCG.getString(0).startsWith("Any"))
                                m = 0;
                            else
                                m = Integer.parseInt(mCG.getString(0));
                        }
                        catch(Exception e)
                        {
                             showError("Please specify a valid month", SearchEngineForm);return;                            
                        }
                        
                        
                        
                        if(d!=0 && m!=0)
                            if(d >this.mlength(2, 2000))
                                {showError("The day of month specified does not exist in the specified month", SearchEngineForm);return;}
                            
                        range = rCG.getSelectedIndex();
                        
                        
                        switch(range)
                        {
                            case 0: range = 0;
                            break;
                            case 1: range = 5;
                            break;
                            case 2: range = 10;
                            break;
                            case 3: range = 50;
                            break;
                            case 4: range = 100;
                            break;
                            case 5: range = 500;
                            break;
                            default: range = 5; break;
                        }
                        
                        
                        
                        if(dow == -1 && d==0 && m==0)
                        {
                            showInfoPause("Please make the search more specific. This query would show all the days in the year range - too broad.", SearchEngineForm);
                            return;
                        }
                        
                        
                        
                        
                        
                        while(true)
                        {
                        
                            if(ResultList.size()==0)
                                break;
                            try
                            {
                                ResultList.delete(0);
                            }
                            catch(Exception e)
                            {break;}
                        }
                        
                        
                        Thread t =  new Thread()                        
                        {
                        public void run()
                            {
                                
                        /*
                        dow
                        dow d
                        dow m
                        dow d m
                        d
                        m
                        d m                        
                        */
                        
                        
                        Calendar cln = Calendar.getInstance();
                        int td,tm,ty;
                        td = cln.get(Calendar.DAY_OF_MONTH);
                        tm = cln.get(Calendar.MONTH)+1;
                        ty = cln.get(Calendar.YEAR);
                        
                        
                        
                            
                        for(int iyear=y;iyear <= y+range ;iyear++)
                        {
                            
                            for(int imonth=1;imonth<=12;imonth++)
                            {
                                if(m!=0)
                                    imonth = m;
                                
                                
                                for(int idom=1;idom<31;idom++)
                                {
                                   waitCanvas.repaint( (waitCanvas.getWidth()-sf.charsWidth(ResultStr,0,ResultStr.length)+2)/2, waitCanvas.getHeight()/8-1,sf.charsWidth(ResultStr,0,ResultStr.length)+2,sf.getHeight() );
                                   waitCanvas.serviceRepaints();
                                    if(cancelSearch)
                                    {
                                       
                                        
                                        
                                        ResultList.setTitle("1 to "+(numRes>10?10:numRes)+" of "+numRes);                    
                                        applyPageCommandRule();
                                        Display.getDisplay(curInstance).setCurrent(ResultList);
                                        return;
                                    }
                                    
                                    
                                    if(d!=0)
                                        idom = d;
                                    
                                        
                                    
                                    if(check(idom, imonth, iyear))
                                    {
                                        if(dow==-1||dow==GetDow(idom,imonth,iyear))
                                        {
                                        
                                                numRes++;    
                                                if(numRes%10==1)
                                                {
                                                    searchV.addElement(new Integer(idom+imonth*100+iyear*10000));      
                                                    System.out.println(idom+imonth*100+iyear*10000);
                                                }
                                                
                                                if(numRes<=10)
                                                    ResultList.append(numRes+". "+getFullDateAndDays(idom, imonth,  iyear, dtd(idom,imonth,iyear, td, tm, ty)),null);
                                                
                                                
                                        
                                        
                                        }
                                    }
                                    
                                        
                                    
                                    if(d!=0)
                                        break;
                                }                                
                                
                                if(m!=0)
                                    break;
                            }                            
                        }
                        
                    ResultList.setTitle("1 to "+(numRes>10?10:numRes)+" of "+numRes);
                    applyPageCommandRule();
                    Display.getDisplay(curInstance).setCurrent(ResultList);
                            
                        }
                        };
                        
                        
                        try{
                            this.cancelSearch = false;                            
                            this.numRes = 0;
                            this.curPage = 0;
                            searchV.removeAllElements();     
                            System.gc();
                            t.start();  
                        }catch(Exception e){Display.getDisplay(curInstance).setCurrent(ResultList);}
                    
                    
                    }
                    else   
                    if(command == this.get_helpCommand1())
                       {
                           showHelp("Please specify the required details of your search query, then press \"OK\" to start searching. The results would be in a list of matches.");
                       } 
                       
                       
                       
                       
                       
                       
                       
                       
                  
                  }
                   
                 if(displayable == ResultList)
                 {
                    if(command==backCommand1)
                    {    
                        searchV.removeAllElements();
                                        try
                                        {
                                            ResultList.removeCommand(nextPageCommand);
                                            
                                        }
                                        catch(Exception e){}

                                        try
                                        {
                                            ResultList.removeCommand(prevPageCommand);
                                            
                                        }
                                        catch(Exception e){}
                        Display.getDisplay(this).setCurrent(SearchEngineForm);
                    }
                    else
                    if(command==List.SELECT_COMMAND)
                        showInfoPause(ResultList.getString(ResultList.getSelectedIndex()), ResultList);
                    else
                    if(command==nextPageCommand)
                    {
                        showNextPage();
                    }
                    else
                    if(command==prevPageCommand)
                    {
                        showPrevPage();
                    }
                    else 
                    if(command == this.get_helpCommand1())
                       {
                           showHelp("Please select the desired result (and press the joystick \"IN\" or \"FIRE\" button for some devices) to see the full description of each match.");
                       } 
                       
                        
                    
                 }
                   
               } 
                   break;
               case LEAPYEARCHECKINDEX:
               {
               if(command == this.get_helpCommand1())
                       {
                           showHelp("Please enter a year to check if it's a leap year, then press the \"OK\" command.");
                       } 
                    
               }
               break;          
           
           
           }
    
       
    }
    
    public void showNextPage()
    {
        Display.getDisplay(this).setCurrent(waitCanvas);
                        waitCanvas.repaint();
                        waitCanvas.serviceRepaints();
                        
          
          
                while(true)
                        {
                         
                            if(ResultList.size()==0)
                                break;
                            try
                            {
                                ResultList.delete(0);
                            }
                            catch(Exception e)
                            {break;}
                        }                   
                        
       System.gc();                 
                                                    
        Thread t =  new Thread()                        
                        {
                        public void run()
                            {
                                
                        int idom,imonth,iyear;
                        Integer nextdate = (Integer) searchV.elementAt(++curPage);
                        idom = nextdate.intValue();
                        
                        iyear = idom/10000;
                        imonth = (idom%10000)/100;
                        idom = idom%100;
                        System.out.println(idom+"/"+imonth+"/"+iyear);
                        
                            
                            /*
                        dow
                        dow d
                        dow m
                        dow d m
                        d
                        m
                        d m                        
                        */
                        
                        
                        Calendar cln = Calendar.getInstance();
                        int td,tm,ty;
                        td = cln.get(Calendar.DAY_OF_MONTH);
                        tm = cln.get(Calendar.MONTH)+1;
                        ty = cln.get(Calendar.YEAR);                        
                        
                        
                        
                            
                        for(;iyear <= y+range ;iyear++)
                        {
                        
                            for(;imonth<=12;imonth++)
                            {
                                if(m!=0)
                                    imonth = m;
                                
                                
                                for(;idom<31;idom++)
                                {
                                     waitCanvas.repaint( (waitCanvas.getWidth()-sf.charsWidth(ResultStr,0,ResultStr.length)+2)/2, waitCanvas.getHeight()/8-1,sf.charsWidth(ResultStr,0,ResultStr.length)+2,sf.getHeight() );
                                   waitCanvas.serviceRepaints();
                                    if(cancelSearch)
                                    {
                                        ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                                                     applyPageCommandRule();
                                                     Display.getDisplay(curInstance).setCurrent(ResultList);
                                        return;
                                    }
                                    
                                    
                                    if(d!=0)
                                        idom = d;
                                    
                                    
                                    
                                    if(check(idom, imonth, iyear))
                                    {
                                            
                                           
                                            
                                        if(dow==-1||dow==GetDow(idom,imonth,iyear))
                                        {
                                        
                                            
                                                 if(ResultList.size()<10)
                                                    ResultList.append((((curPage)*10)+ResultList.size()+1)+". "+getFullDateAndDays(idom, imonth,  iyear, dtd(idom,imonth,iyear, td, tm, ty)),null);
                                                else
                                                    {   
                                                    
                                                     
                                                     ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                                                     applyPageCommandRule();
                                                     Display.getDisplay(curInstance).setCurrent(ResultList);
                                                     
                                                     
                                                     
                                                     return;
                                                    }
                                        
                                        }
                                    }
                                    
                                    
                                    
                                    if(d!=0)
                                        break;
                                }
                                idom=1;
                                
                                if(m!=0)
                                    break;
                            }    
                            imonth =1;
                        }
                        
                    
                    ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                    applyPageCommandRule();
                    Display.getDisplay(curInstance).setCurrent(ResultList);
                            
                        }
                        };
                        
                        
                        try{
                            this.cancelSearch = false;                                                        
                            t.start();  
                        }catch(Exception e){Display.getDisplay(curInstance).setCurrent(ResultList);}
    }
    public void showPrevPage()
    {
        
        Display.getDisplay(this).setCurrent(waitCanvas);
                        waitCanvas.repaint();
                        waitCanvas.serviceRepaints();
                        
          
          
                while(true)
                        {
                            
                            if(ResultList.size()==0)
                                break;
                            try
                            {
                                ResultList.delete(0);
                            }
                            catch(Exception e)
                            {break;}
                        }                   
                        
            System.gc();            
                                                    
        Thread t =  new Thread()                        
                        {
                        public void run()
                            {
                                
                        int idom,imonth,iyear;
                        Integer nextdate = (Integer) searchV.elementAt(--curPage);
                        idom = nextdate.intValue();
                        
                        iyear = idom/10000;
                        imonth = (idom%10000)/100;
                        idom = idom%100;
                        
                            
                            /*
                        dow
                        dow d
                        dow m
                        dow d m
                        d
                        m
                        d m                        
                        */
                        
                        
                        Calendar cln = Calendar.getInstance();
                        int td,tm,ty;
                        td = cln.get(Calendar.DAY_OF_MONTH);
                        tm = cln.get(Calendar.MONTH)+1;
                        ty = cln.get(Calendar.YEAR);                        
                        
                        
                        
                            
                        for(;iyear <= y+range ;iyear++)
                        {
                        
                            for(;imonth<=12;imonth++)
                            {
                                if(m!=0)
                                    imonth = m;
                                
                                
                                for(;idom<31;idom++)
                                {
                                     waitCanvas.repaint( (waitCanvas.getWidth()-sf.charsWidth(ResultStr,0,ResultStr.length)+2)/2, waitCanvas.getHeight()/8-1,sf.charsWidth(ResultStr,0,ResultStr.length)+2,sf.getHeight() );
                                   waitCanvas.serviceRepaints();
                                    if(cancelSearch)
                                    {
                                        ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                                                     applyPageCommandRule();
                                                     Display.getDisplay(curInstance).setCurrent(ResultList);
                                        return;
                                    }
                                    
                                    
                                    if(d!=0)
                                        idom = d;
                                    
                                        
                                    
                                    if(check(idom, imonth, iyear))
                                    {
                                            
                                           
                                            
                                        if(dow==-1||dow==GetDow(idom,imonth,iyear))
                                        {
                                        
                                            
                                                 if(ResultList.size()<10)
                                                    ResultList.append((((curPage)*10)+ResultList.size()+1)+". "+getFullDateAndDays(idom, imonth,  iyear, dtd(idom,imonth,iyear, td, tm, ty)),null);
                                                else
                                                    {   
                                                    
                                                     
                                                     ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                                                     applyPageCommandRule();
                                                     Display.getDisplay(curInstance).setCurrent(ResultList);
                                                     
                                                     
                                                     
                                                     return;
                                                    }
                                        
                                        }
                                    }
                                    
                                        
                                    
                                    if(d!=0)
                                        break;
                                }
                                idom=1;
                                
                                if(m!=0)
                                    break;
                            }    
                            imonth =1;
                        }
                    
                    
                    ResultList.setTitle(((curPage)*10+1)+" to "+(((curPage)*10)+ResultList.size())+" of "+numRes);
                    applyPageCommandRule();
                    Display.getDisplay(curInstance).setCurrent(ResultList);
                            
                        }
                        };
                        
                        
                        try{
                            this.cancelSearch = false;                                                        
                            t.start();  
                        }catch(Exception e){Display.getDisplay(curInstance).setCurrent(ResultList);}
              
    
    }
    public void applyPageCommandRule()
    {
         try
                                        {
                                            ResultList.removeCommand(nextPageCommand);
                                            
                                        }
                                        catch(Exception e){}

                                        try
                                        {
                                            ResultList.removeCommand(prevPageCommand);
                                            
                                        }
                                        catch(Exception e){}
        
        
        if(searchV.size()-1>curPage)
            ResultList.addCommand(nextPageCommand);
        if(curPage>0)
            ResultList.addCommand(prevPageCommand);
        
    }
    public int GetDow(int d, int m, int y)
    {
        int dow,today; 
        Calendar c = Calendar.getInstance();
        long totald = this.dtd(c.get(c.DAY_OF_MONTH), c.get(c.MONTH)+1, c.get(c.YEAR), d, m, y);
       today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
       
       if(past(d,m,y))
       {long tmpint = today-(totald%7);dow = (int) ((tmpint<=0)?(7+(tmpint)):tmpint);}
        else
	{dow = today +((int) (totald%7)); if(dow>7) dow -= 7;}
        return dow;
    }
    
    public void itemStateChanged(Item item)
      {
         
        if (item==dowCG)
         {
            if(dowCG.getSelectedIndex()==1)
            {
                DOWList.insert(0, "Any", null);
                DOWList.setTitle("Day of Week:");
                Display.getDisplay(this).setCurrent(DOWList);
                dowCG.setSelectedIndex(0, true);            
            }
         }
         
         
         if(item==dCG)
         {
            if(dCG.getSelectedIndex()==1)
            {
                get_OneNumberForm().setTitle("Day of Month");
                ((StringItem)get_OneNumberForm().get(0)).setText("Please enter the desired day of month to match. You can enter 0 or leave empty to imply ANY day of month.");
                ((StringItem)get_OneNumberForm().get(0)).setLabel("Input");
                ((TextField)get_OneNumberForm().get(1)).setLabel("Day of Month:");
                try
                {
                    Integer.parseInt(dCG.getString(0));
                    ((TextField)get_OneNumberForm().get(1)).setString(dCG.getString(0));
                }
                catch(Exception e){    ((TextField)get_OneNumberForm().get(1)).setString(null);  }
                
                Display.getDisplay(this).setCurrent(OneNumberForm);
                System.gc();
            }
            
         }
         
         
          if(item==mCG)
         {
            
             if(mCG.getSelectedIndex()==1)
            {                                
                get_OneNumberForm().setTitle("Month");
                ((StringItem)get_OneNumberForm().get(0)).setText("Please enter the desired month to match. For example, enter 12 for December. You can enter 0 or leave empty to imply ANY month.");
                ((StringItem)get_OneNumberForm().get(0)).setLabel("Input");
                ((TextField)get_OneNumberForm().get(1)).setLabel("Month:");
                
                try
                {
                    Integer.parseInt(mCG.getString(0));
                    ((TextField)get_OneNumberForm().get(1)).setString(mCG.getString(0));
                }
                catch(Exception e){    ((TextField)get_OneNumberForm().get(1)).setString(null);}
                
                Display.getDisplay(this).setCurrent(OneNumberForm);
                System.gc();
            }
            
         }
         
             
      }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public long[] getDaysWeeksFromForm(Form f) throws Exception
    {
        long d,w;
                        try
                        {
                            d = Long.parseLong(((TextField)f.get(0)).getString());
                            
                        }
                        catch(Exception e)
                        {
                         /*   showError("Please enter the number of days.",f);
                            throw new Exception();*/ d=0;
                        }
                        try
                        {
                            w = Long.parseLong(((TextField)f.get(1)).getString());
                        }
                        catch(Exception e)
                        {
                            /*showError("Please enter the number of weeks.",f);
                            throw new Exception();*/
                            w=0;
                        }
                        
                                                           
                        if(d < 0)
                        {    
                            showError("Zero or negative days not allowed",f);
                            throw new Exception();
                        }
                        if(w < 0)
                        {    
                            showError("Zero or negative weeks not allowed",f);
                            throw new Exception();
                        }
                        if(d==0&&w==0)
                        {
                            showError("Nothing to count: days and weeks are zero.",f);
                            throw new Exception();
                        }    
                        
       
         return new long[] {d,w};
        
    }
    
    public void initDateForm(Form f)
    {
        Calendar c = Calendar.getInstance();
        ((TextField)f.get(0)).setString(Integer.toString(c.get(c.DAY_OF_MONTH)));
        ((TextField)f.get(1)).setString(Integer.toString(c.get(c.MONTH)+1));
        ((TextField)f.get(2)).setString(Integer.toString(c.get(c.YEAR)));
    }
    
    public int[] getDateFormDate(Form f) throws Exception
    {
        /* boolean mNotIn = true;
         boolean yNotIn = true;
         boolean dNotIn = true;*/
         int d,m,y;
                                                
                        try
                        {
                            d = Integer.parseInt(((TextField)f.get(0)).getString());                            
                        }
                        catch(Exception e)
                        {
                            showError("Please enter the day of month",f);
                            throw new Exception();
                        }
                        try
                        {
                            m = Integer.parseInt(((TextField)f.get(1)).getString());
                        }
                        catch(Exception e)
                        {
                            showError("Please enter the month",f);
                            throw new Exception();
                        }
                        try
                        {
                            y = Integer.parseInt(((TextField)f.get(2)).getString());
                        }                   
                        catch(Exception e)
                        {
                            showError("Please enter the year",f);
                            throw new Exception();
                        }
                        
                                                           
                        if(y <= 0)
                        {    
                            showError("Zero or negative years not allowed",f);
                            throw new Exception();
                        }
                        if(m <= 0)
                        {    
                            showError("Zero or negative months not allowed",f);
                            throw new Exception();
                        }
                        if(d <= 0)
                        {    
                            showError("Zero or negative days not allowed",f);
                            throw new Exception();
                        }
                        if(!check(d,m,y))
                        {
                            showError("Invalid Date: The date combination entered does not exist.",f);
                            throw new Exception();
                        }
       
         return new int[] {d,m,y};
    }
   boolean verifyAndCalcQuery()
   {
       String tmp=null;
       try
       {
         tmp = yTF.getString();
         
         if(tmp==null || tmp.equals(""))           
         {
            this.showInfoPause("Please specify the year.", SearchEngineForm);
            return false;            
         }
         
         int y=0;
         y = Integer.parseInt(tmp);
         
         
       }
       catch(Exception e)
       {
            showError("Please specify the year.", SearchEngineForm);
            return false;                 
       }
       
       return true;   
   }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public String getFullDate(Calendar c)
   {
        String tmpS = "";
        switch(c.get(Calendar.DAY_OF_WEEK))
        {
             case Calendar.MONDAY:  tmpS = "Monday";break;   
            case Calendar.TUESDAY: tmpS = "Tuesday";break;   
            case Calendar.WEDNESDAY: tmpS = "Wednesday";break;   
            case Calendar.THURSDAY: tmpS = "Thursday";break;      
            case Calendar.FRIDAY: tmpS = "Friday";break;      
            case Calendar.SATURDAY:tmpS = "Saturday";break;      
            case Calendar.SUNDAY:tmpS = "Sunday";break;             
        }
        tmpS += " " + Integer.toString(c.get(Calendar.DAY_OF_MONTH)) +" ";
        
        switch(c.get(Calendar.MONTH))
        {
           case Calendar.JANUARY: tmpS += "January";break;
           case Calendar.FEBRUARY: tmpS += "February";break;   
            case Calendar.MARCH: tmpS += "March";break;   
            case Calendar.APRIL: tmpS += "April";break;      
            case Calendar.MAY: tmpS += "May";break;      
            case Calendar.JUNE:tmpS += "June";break;      
            case Calendar.JULY:tmpS += "July";break;                   
            case Calendar.AUGUST:tmpS += "August";break;                   
            case Calendar.SEPTEMBER:tmpS += "September";break;                   
            case Calendar.OCTOBER:tmpS += "October";break;                   
            case Calendar.NOVEMBER:tmpS += "November";break;                   
            case Calendar.DECEMBER:tmpS += "December";break;                 
       }
         tmpS +=" ";
        if(c.get(Calendar.YEAR) > 0)
        {
            tmpS += Integer.toString(c.get(Calendar.YEAR));
            tmpS+=" ("+c.get(c.DATE)+"/"+(c.get(c.MONTH)+1)+"/"+c.get(c.YEAR)+")";
        }
        else
        {
            tmpS += Integer.toString(java.lang.Math.abs(c.get(Calendar.YEAR)-1));
            tmpS += " B.C. ";
            tmpS+=" ("+c.get(c.DATE)+"/"+(c.get(c.MONTH)+1)+"/"+(c.get(c.YEAR)-1)+")";
        }
        
        return(tmpS);
        
   }    
   public String GetStringForTicker()
   {
        //char[] cs = {'c','c'};
        //return new String(cs);
        return "Today: "+getFullDate(Calendar.getInstance())+" \"CONSIDER the flight of time!\" ";   
   }
   
   
   
   
   
   
   
   
     public boolean past(int d2, int m2, int y2)
   {
       int d1,m1,y1; 
       Calendar c = Calendar.getInstance();
       d1 = c.get(Calendar.DATE);
       m1 = c.get(Calendar.MONTH) + 1 ;
       y1 = c.get(Calendar.YEAR);
       
        if( ( y2 < y1) || ( y1 == y2 && m2 < m1 ) || ( y1 == y2 && m1 == m2 && d2 < d1) )
            return true;    
        return false; 
   }    
   public String getFullDateAndDays(int d,int m,int y, long totald)
   {
        return(getFullDate(d,m,y,totald) + " ("+totald+(past(d,m,y)?" day(s) ago).":" day(s) away)."));
   }
   
   public String getFullDate(int d,int m,int y, long totald)
   {
       long dow,today; 
       today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
       
       if(past(d,m,y))
       {long tmpint = today-(totald%7);dow = (tmpint<=0)?(7+(tmpint)):tmpint;}
        else
	{dow = today + (totald%7); if(dow>7) dow -= 7;}
	String tmpS = null;
       switch((int)dow)
        {
            case 2:  tmpS = "Monday";break;   
            case 3: tmpS = "Tuesday";break;   
            case 4: tmpS = "Wednesday";break;   
            case 5: tmpS = "Thursday";break;      
            case 6: tmpS = "Friday";break;      
            case 7:tmpS = "Saturday";break;      
            case 1:tmpS = "Sunday";break;                    
        }
        tmpS += " " + Integer.toString(d) +" ";
        
        switch(m)
        {
            case 1:  tmpS += "January";break;   
            case 2: tmpS += "February";break;   
            case 3: tmpS += "March";break;   
            case 4: tmpS += "April";break;      
            case 5: tmpS += "May";break;      
            case 6:tmpS += "June";break;      
            case 7:tmpS += "July";break;                   
            case 8:tmpS += "August";break;                   
            case 9:tmpS += "September";break;                   
            case 10:tmpS += "October";break;                   
            case 11:tmpS += "November";break;                   
            case 12:tmpS += "December";break;                   
       }
        tmpS +=" ";
        
        if(y > 0)
        {tmpS += Integer.toString(y);
            tmpS+=" ("+d+"/"+m+"/"+y+")";}
        else
        {   
            tmpS += Integer.toString(java.lang.Math.abs(y-1));//no "year 0"
            tmpS += " B.C. ";
            tmpS+=" ("+d+"/"+m+"/"+(y-1)+")";
        }
                
        
        
        return(tmpS);        
        
        
        
   }
   
   
   
    
long dtd(int d1, int m1, int y1, int d2,int m2,int y2)
{
     long  totald, dofyrsbtw, stm2;
     int  n, btw;         
            if( ( y2 < y1) || ( y1 == y2 && m2 < m1 ) || ( y1 == y2 && m1 == m2 && d2 < d1) )
		{
			
			int trans;
			trans = y1;
			y1 = y2;
			y2 = trans;
			trans = m1;
			m1 = m2;
			m2 = trans;
			trans = d1;
			d1 = d2;
			d2 = trans;
		}
	if ( y1 == y2)
	{
		if( m1 == m2 )
		totald = d2 - d1;
		else
		{
			int t1m1 = m1;
			btw = 0;
			while(true)
			{
				if( ( m2 - t1m1) == 1)
				break;
				btw += mlength(++t1m1, y1);
			}
			totald = mlength(m1, y1) - d1 + d2 +btw;
		}

	}
	else
	{
		int t2m1, m1tend, t1y1;
		t2m1 = m1;
		m1tend = 0;
		while(true)
		{
			if(t2m1 == 12)
			break;
			m1tend += mlength(++t2m1, y1);
		}
		dofyrsbtw = 0;
		t1y1 = y1;
		while(true)
		{
			if( y2 - t1y1 == 1)
			break;
			dofyrsbtw += ylength( ++t1y1);
		}
		stm2 = 0;
		n = 0;
		while(true)
		{
			if(m2 == n + 1)
			break;
			stm2 += mlength(++n, y2);
			if(n == m2 - 1 )
			break;
		}
		totald = mlength(m1, y1) - d1 + m1tend + dofyrsbtw + stm2 + d2;
	}
	
        return(totald);
    
}    



int mlength(int m, int y)
	{
	int retml = 0;
	switch(m)
		{
			case 1 :  retml = 31;break;
			case 2 :  if(  (y%4 == 0  &&  y%100 != 0) || ( y%400 == 0)  ) retml = 29 ; else retml = 28;break;
			case 3 :  retml = 31;break;
			case 4 :  retml = 30;break;
			case 5 :  retml = 31;break;
			case 6 :  retml = 30;break;
			case 7 :  retml = 31;break;
			case 8 :  retml = 31;break;
			case 9 :   retml = 30;break;
			case 10 :  retml = 31;break;
			case 11 :  retml = 30;break;
			case 12 :  retml = 31;break;
		}
	return(retml);
	}
boolean check(int d, int m, int y)
{
	return ( ( m>0 && m < 13) && ( (d>0) && (d <= mlength(m, y))) );
}
int ylength( int y)
{
	int doy;
	if(  (y%4 == 0  &&  y%100 != 0) || ( y%400 == 0)  ) doy = 366; else doy = 365;
	return(doy);
}
public void getAndShowNextDOW(int dow)
{
    Calendar c = Calendar.getInstance();
    
    dow = (dow - c.get(Calendar.DAY_OF_WEEK));
    if(dow<=0)
        dow+=7;
    
    int[] ret =  daysPlusDate((long)dow,c.get(c.DAY_OF_MONTH),c.get(c.MONTH)+1,c.get(c.YEAR));
    //c.setTime(new Date(c.getTime().getTime() - (long)(dow*86400000)));
    String tmpS = getFullDateAndDays(ret[0],ret[1],ret[2], (long)dow);
    showInfoPause(tmpS, DOWList);
    c = null;
    ret = null;
    //showInfo(tmpS, nextScreen);
    tmpS=null;    
    
}
public void getAndShowLastDOW(int dow)
{
    Calendar c = Calendar.getInstance();
    
    dow = (c.get(Calendar.DAY_OF_WEEK) - dow );
    if(dow<=0)
        dow+=7;
    int[] ret =  daysMinusDate((long)dow,c.get(c.DAY_OF_MONTH),c.get(c.MONTH)+1,c.get(c.YEAR));
    //c.setTime(new Date(c.getTime().getTime() - (long)(dow*86400000)));
    String tmpS = getFullDateAndDays(ret[0],ret[1],ret[2], (long)dow);
    showInfoPause(tmpS, DOWList);
    c = null;
    ret = null;
    //showInfo(tmpS, nextScreen);
    tmpS=null;    
    
}

public int[] daysPlusDate(long n,int d,int m,int y)
{
	
	if(n <= mlength(m,y) - d)
		{ d += n;	
			}else{n+=d;
	while(true)
	{
		
		n -= (mlength(m,y));
		if(m<12)	
		 m++;
		else
		{
			m = 1;
			y++;
		}	 

		
		if(n <= mlength(m,y))
		{	d = (int) n;
			break;
			}
			
				
	}	
	}
	
	return new int[] {d,m,y};
}
public int[] daysMinusDate(long n,int d,int m,int y)
{
	
	if(n < d)
		{ d -= n;
			}else{n-=d;if(m==1)
		{y--;m=12;}
		else
		m--;
	while(true)
	{
		
		
		if(n < mlength(m,y))
		{	d = mlength(m,y) - (int)n;
			break;
			}
		
		
		n -= (mlength(m,y));
		if(m==1)
		{y--;m=12;}
		else
		m--;
		
		
	
		
			
				
	}	
	}
	
	return new int[] {d,m,y};
}
               

               public static final int COUNTDOWNINDEX = 0;              
                      
               public static final int COUNTTOFUTUREINDEX = 1;                 
                   
               public static final int COUNTTOPASTINDEX = 2;                   
                   
               public static final int BETWEENDATESINDEX = 3;                   
                         
               public static final int DATESEARCHENGINEINDEX = 4;                   
               
               public static final int NEXTDOWINDEX = 5;                   
                   
               public static final int LASTDOWINDEX = 6;                   
                   
               public static final int DPDINDEX = 7;                   
                   
               public static final int DMDINDEX = 8;                                  
                   
               public static final int LEAPYEARCHECKINDEX = 9;                   
                   
               public List DOWList;
               
               public List ResultList;
               
               int curPage, numRes;
               
               long prevMem;
}
