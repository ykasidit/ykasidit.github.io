/*
Copyright (c) 2006-2008 Kasidit Yusuf
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

package Pregtool;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import java.util.*;


public class Pregtool extends MIDlet implements CommandListener,ItemStateListener {
    
    /** Creates a new instance of HelloMidlet */
    public Pregtool() {
    }
    
    private Form mainForm;//GEN-BEGIN:MVDFields
    private Command exitCommand;
    private TextField LMPField;
    private TextField GAWeeksField;
    private StringItem ResultEDDItem;
    private StringItem ResultGAItem;
    private Command helpCommand;
    private Command clearCommand;
    private Command aboutCommand;
    private Form textForm;
    private Command exitCommand1;
    private Command backCommand1;
    private StringItem textItem;//GEN-END:MVDFields
    private String prevLMPStr;
    private String prevGAWStr;
    private String prevGADStr;
//GEN-LINE:MVDMethods

    /** This method initializes UI of the application.//GEN-BEGIN:MVDInitBegin
     */
    private void initialize() {//GEN-END:MVDInitBegin
        // Insert pre-init code here
        getDisplay().setCurrent(get_mainForm());//GEN-LINE:MVDInitInit
        // Insert post-init code here
    }//GEN-LINE:MVDInitEnd
    
    /** Called by the system to indicate that a command has been invoked on a particular displayable.//GEN-BEGIN:MVDCABegin
     * @param command the Command that ws invoked
     * @param displayable the Displayable on which the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:MVDCABegin
        // Insert global pre-action code here
        if (displayable == mainForm) {//GEN-BEGIN:MVDCABody
            if (command == exitCommand) {//GEN-END:MVDCABody
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction3
                // Insert post-action code here
            } else if (command == helpCommand) {//GEN-LINE:MVDCACase3
                showHelp();
                // Do nothing//GEN-LINE:MVDCAAction13
                // Insert post-action code here
            } else if (command == clearCommand) {//GEN-LINE:MVDCACase13
                this.get_LMPField().setString("");
                this.get_GAWeeksField().setString("");                
                itemStateChanged(null); //re-calculate
                // Do nothing//GEN-LINE:MVDCAAction15
                // Insert post-action code here
            } else if (command == aboutCommand) {//GEN-LINE:MVDCACase15
                showAbout();
                // Do nothing//GEN-LINE:MVDCAAction18
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase18
        } else if (displayable == textForm) {
            if (command == backCommand1) {//GEN-END:MVDCACase18
                
                getDisplay().setCurrent(get_mainForm());//GEN-LINE:MVDCAAction24
                get_textItem().setText(null);
            } else if (command == exitCommand1) {//GEN-LINE:MVDCACase24
                // Insert pre-action code here
                exitMIDlet();//GEN-LINE:MVDCAAction22
                // Insert post-action code here
            }//GEN-BEGIN:MVDCACase22
        }//GEN-END:MVDCACase22
        // Insert global post-action code here
}//GEN-LINE:MVDCAEnd
    
    /**
     * This method should return an instance of the display.
     */
    public Display getDisplay() {//GEN-FIRST:MVDGetDisplay
        return Display.getDisplay(this);
    }//GEN-LAST:MVDGetDisplay
    
    /**
     * This method should exit the midlet.
     */
    public void exitMIDlet() {//GEN-FIRST:MVDExitMidlet
        getDisplay().setCurrent(null);
        destroyApp(true);
        notifyDestroyed();
    }//GEN-LAST:MVDExitMidlet
    
    /** This method returns instance for mainForm component and should be called instead of accessing mainForm field directly.//GEN-BEGIN:MVDGetBegin2
     * @return Instance for mainForm component
     */
    public Form get_mainForm() {
        if (mainForm == null) {//GEN-END:MVDGetBegin2
            // Insert pre-init code here
            mainForm = new Form("Pregtool", new Item[] {//GEN-BEGIN:MVDGetInit2
                get_ResultEDDItem(),
                get_ResultGAItem(),
                get_LMPField(),
                get_GAWeeksField()
            });
            mainForm.addCommand(get_clearCommand());
            mainForm.addCommand(get_helpCommand());
            mainForm.addCommand(get_aboutCommand());
            mainForm.addCommand(get_exitCommand());
            mainForm.setCommandListener(this);//GEN-END:MVDGetInit2
            mainForm.setItemStateListener(this);
        }//GEN-BEGIN:MVDGetEnd2
        return mainForm;
    }//GEN-END:MVDGetEnd2
    
    
    /** This method returns instance for exitCommand component and should be called instead of accessing exitCommand field directly.//GEN-BEGIN:MVDGetBegin5
     * @return Instance for exitCommand component
     */
    public Command get_exitCommand() {
        if (exitCommand == null) {//GEN-END:MVDGetBegin5
            // Insert pre-init code here
            exitCommand = new Command("Exit", Command.EXIT, 4);//GEN-LINE:MVDGetInit5
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd5
        return exitCommand;
    }//GEN-END:MVDGetEnd5

    /** This method returns instance for LMPField component and should be called instead of accessing LMPField field directly.//GEN-BEGIN:MVDGetBegin6
     * @return Instance for LMPField component
     */
    public TextField get_LMPField() {
        if (LMPField == null) {//GEN-END:MVDGetBegin6
            // Insert pre-init code here
            LMPField = new TextField("1. LMP or US (ddmmyy)", "", 6, TextField.NUMERIC);//GEN-LINE:MVDGetInit6
            Calendar c = Calendar.getInstance();
            String s="";
            String tmps=null;
            
            tmps =Integer.toString(c.get(c.DAY_OF_MONTH));            
            if(tmps.length() == 1)
                tmps = "0"+tmps;                        
            s+=tmps;
            
            tmps =Integer.toString(c.get(c.MONTH)+1);            
            if(tmps.length() == 1)
                tmps = "0"+tmps;                        
            s+=tmps;
            
            tmps =Integer.toString(c.get(c.YEAR)%100);            
            if(tmps.length() == 1)
                tmps = "0"+tmps;                        
            s+=tmps;
            
            LMPField.setString(s);
        }//GEN-BEGIN:MVDGetEnd6
        return LMPField;
    }//GEN-END:MVDGetEnd6

    /** This method returns instance for GAWeeksField component and should be called instead of accessing GAWeeksField field directly.//GEN-BEGIN:MVDGetBegin7
     * @return Instance for GAWeeksField component
     */
    public TextField get_GAWeeksField() {
        if (GAWeeksField == null) {//GEN-END:MVDGetBegin7
            // Insert pre-init code here
            GAWeeksField = new TextField("2. GA in US  (wwd)", "", 3, TextField.NUMERIC);//GEN-LINE:MVDGetInit7
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd7
        return GAWeeksField;
    }//GEN-END:MVDGetEnd7

    /** This method returns instance for ResultEDDItem component and should be called instead of accessing ResultEDDItem field directly.//GEN-BEGIN:MVDGetBegin9
     * @return Instance for ResultEDDItem component
     */
    public StringItem get_ResultEDDItem() {
        if (ResultEDDItem == null) {//GEN-END:MVDGetBegin9
            // Insert pre-init code here
            ResultEDDItem = new StringItem("EDD:", "<Please enter new LMP or US>\n");//GEN-LINE:MVDGetInit9
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd9
        return ResultEDDItem;
    }//GEN-END:MVDGetEnd9

    /** This method returns instance for ResultGAItem component and should be called instead of accessing ResultGAItem field directly.//GEN-BEGIN:MVDGetBegin10
     * @return Instance for ResultGAItem component
     */
    public StringItem get_ResultGAItem() {
        if (ResultGAItem == null) {//GEN-END:MVDGetBegin10
            // Insert pre-init code here
            ResultGAItem = new StringItem("GA Today:", "<Please enter new LMP or US>");//GEN-LINE:MVDGetInit10
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd10
        return ResultGAItem;
    }//GEN-END:MVDGetEnd10
 
    /** This method returns instance for helpCommand component and should be called instead of accessing helpCommand field directly.//GEN-BEGIN:MVDGetBegin12
     * @return Instance for helpCommand component
     */
    public Command get_helpCommand() {
        if (helpCommand == null) {//GEN-END:MVDGetBegin12
            // Insert pre-init code here
            helpCommand = new Command("Help", Command.HELP, 2);//GEN-LINE:MVDGetInit12
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd12
        return helpCommand;
    }//GEN-END:MVDGetEnd12

    /** This method returns instance for clearCommand component and should be called instead of accessing clearCommand field directly.//GEN-BEGIN:MVDGetBegin14
     * @return Instance for clearCommand component
     */
    public Command get_clearCommand() {
        if (clearCommand == null) {//GEN-END:MVDGetBegin14
            // Insert pre-init code here
            clearCommand = new Command("Clear", Command.OK, 1);//GEN-LINE:MVDGetInit14
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd14
        return clearCommand;
    }//GEN-END:MVDGetEnd14

    /** This method returns instance for aboutCommand component and should be called instead of accessing aboutCommand field directly.//GEN-BEGIN:MVDGetBegin17
     * @return Instance for aboutCommand component
     */
    public Command get_aboutCommand() {
        if (aboutCommand == null) {//GEN-END:MVDGetBegin17
            // Insert pre-init code here
            aboutCommand = new Command("About", Command.HELP, 3);//GEN-LINE:MVDGetInit17
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd17
        return aboutCommand;
    }//GEN-END:MVDGetEnd17
    /** This method returns instance for textForm component and should be called instead of accessing textForm field directly.//GEN-BEGIN:MVDGetBegin20
     * @return Instance for textForm component
     */
    public Form get_textForm() {
        if (textForm == null) {//GEN-END:MVDGetBegin20
            // Insert pre-init code here
            textForm = new Form(null, new Item[] {get_textItem()});//GEN-BEGIN:MVDGetInit20
            textForm.addCommand(get_backCommand1());
            textForm.addCommand(get_exitCommand1());
            textForm.setCommandListener(this);//GEN-END:MVDGetInit20
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd20
        return textForm;
    }//GEN-END:MVDGetEnd20

    /** This method returns instance for exitCommand1 component and should be called instead of accessing exitCommand1 field directly.//GEN-BEGIN:MVDGetBegin21
     * @return Instance for exitCommand1 component
     */
    public Command get_exitCommand1() {
        if (exitCommand1 == null) {//GEN-END:MVDGetBegin21
            // Insert pre-init code here
            exitCommand1 = new Command("Exit", Command.EXIT, 4);//GEN-LINE:MVDGetInit21
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd21
        return exitCommand1;
    }//GEN-END:MVDGetEnd21

    /** This method returns instance for backCommand1 component and should be called instead of accessing backCommand1 field directly.//GEN-BEGIN:MVDGetBegin23
     * @return Instance for backCommand1 component
     */
    public Command get_backCommand1() {
        if (backCommand1 == null) {//GEN-END:MVDGetBegin23
            // Insert pre-init code here
            backCommand1 = new Command("Back", Command.BACK, 1);//GEN-LINE:MVDGetInit23
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd23
        return backCommand1;
    }//GEN-END:MVDGetEnd23

    /** This method returns instance for textItem component and should be called instead of accessing textItem field directly.//GEN-BEGIN:MVDGetBegin25
     * @return Instance for textItem component
     */
    public StringItem get_textItem() {
        if (textItem == null) {//GEN-END:MVDGetBegin25
            // Insert pre-init code here
            textItem = new StringItem("", "");//GEN-LINE:MVDGetInit25
            // Insert post-init code here
        }//GEN-BEGIN:MVDGetEnd25
        return textItem;
    }//GEN-END:MVDGetEnd25
    
    public void startApp() {
        initialize();
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
     public void showHelp()
    {
         
        this.get_textForm().setTitle("Help");
        this.get_textItem().setText("Use Pregtool to calculate the EDD (End Due Date or Delivery Date) and GA (Gestational Age) from the entered:\n 1.LMP (Last Menstrual Period) or US (date of UltraSound).\n 2.GA in US (Gestational Age in UltraSound), at least 4 weeks and not more than 40 weeks - this software would imply the non-wwd format input according to this rule.");
        Display.getDisplay(this).setCurrent(get_textForm());        
    }
     public void setErrorText(String err)
     {
        this.get_ResultEDDItem().setText(err);
        this.get_ResultGAItem().setText(err);     
     }
     
     public synchronized void itemStateChanged(Item item)
     {
        
        int d=0,m=0,y=0;
        int gausD=0,gausW=0;
        
        
        
        String lmpf = get_LMPField().getString();
        if(lmpf.length()==5)
            lmpf = "0"+lmpf;
        String gausWf = this.get_GAWeeksField().getString();        
        
        
        //System.out.println("rein");
        if(item!=null)
        {
            if(prevLMPStr!=null && lmpf!=null && prevLMPStr.equals(lmpf))
            {            
              //  System.out.println("rein1");

                if(prevGAWStr!=null && gausWf!=null && prevGAWStr.equals(gausWf))
                {
                //    System.out.println("rein2");

                    return;
                }
            }
        }
        prevLMPStr = lmpf;
        prevGAWStr = gausWf;
        
        
        
        try        
        {
            if(lmpf.length()<6)
                throw new Exception();
            
            String ds=null;
            String ms=null;
            String ys=null;
            ds = lmpf.substring(0,2);
            ms = lmpf.substring(2,4);
            ys = lmpf.substring(4,6);
            
            d = Integer.parseInt(ds);            
            m = Integer.parseInt(ms);            
            y = Integer.parseInt(ys);            
            y+=2000;
            if(!check(d,m,y))
            {
            setErrorText("LMP date does not exist");
            return;            
            }
            
            if(!past(d,m,y)) //if future date
            {
                setErrorText("Future dates are not applicable");
                return;            
            }
        }
        catch(Exception e)
        {
           setErrorText("Please enter LMP in ddmmyy");
           return;
        }
        
        
        
        if(gausWf!=null)        
            gausWf = gausWf.trim();
        
        if(gausWf!=null&&!gausWf.equals("0")&&!gausWf.equals(""))
        {
            try
            {
            gausW = Integer.parseInt(gausWf);
            
            if(gausW<0) 
            {
                setErrorText("Negative Weeks are not applicable");
                return;            
            }
            
            
            
            
            
            if(gausW<4)
            {           
                
                setErrorText("GA not less than 4 weeks");
                return;
             
            }
            else
            if(gausW<40)
            {           
                
             //use it as weeks   
                
                if(prevGAWStr.length()==3)
                {
                setErrorText("GA not less than 4 weeks");
                return;
                }
            }
            else// > 40
            if(gausW<407)
            {
                gausD = gausW%10;
                gausW /= 10;
            }
            else            
            {
                setErrorText("GA not more than 40 weeks");
                return;                
            }
            
                    
            }
            catch(Exception e)
            {
            setErrorText("Invalid GA in US Weeks");
            return;
            }
        }
        
        
        
        
        
        
        
        
        
        long dbtw = 280 - (gausD+(gausW*7));
        
        int[] resdmy = daysPlusDate(dbtw,d,m,y);
        
        
        Calendar calendar2 = Calendar.getInstance();
        
        long resfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),resdmy[0],resdmy[1],resdmy[2]);        
        //long startfromtoday = dtd(calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR),dmy[0],dmy[1],dmy[2]);        
        
        
        String tmpS = this.getFullDate(resdmy[0], resdmy[1],resdmy[2], resfromtoday);//+" is "+dbtw+" day(s) after "+this.getFullDate(dmy[0], dmy[1],dmy[2], startfromtoday)+".";
        tmpS+="\n";
        this.get_ResultEDDItem().setText(tmpS);
        
        //now use dbtw for GA
        dbtw = dtd(d,m,y,calendar2.get(Calendar.DATE),calendar2.get(Calendar.MONTH)+1,calendar2.get(Calendar.YEAR));        
        dbtw = Math.abs(dbtw);
        //System.out.println(dbtw);
        dbtw +=(gausD+(gausW*7));        
        //System.out.println(dbtw);
        tmpS = "";      
        
                        if(dbtw/7 > 0)        
                        {                       
                            tmpS += dbtw/7 + " week(s)";
                             if(dbtw%7>0)
                             {
                             tmpS+=" and ";                                     
                             }    
                                 
                        }
        
                            if(dbtw%7>0)
                            {
                                tmpS+=dbtw%7+" day(s)";
                            }
                                
                 if(dbtw==0)
                 {
                    tmpS = "Less than 1 Day";
                 }
        
        this.get_ResultGAItem().setText(tmpS);
                        
        
            
     }
     
     public String getFullDateAndDays(int d,int m,int y, long totald)
   {
        return(getFullDate(d,m,y,totald) + " ("+totald+(past(d,m,y)?" day(s) ago).":" day(s) away)."));
   }
     public boolean past(int d2, int m2, int y2)
   {
       int d1,m1,y1; 
       Calendar c = Calendar.getInstance();
       d1 = c.get(Calendar.DATE);
       m1 = c.get(Calendar.MONTH) + 1 ;
       y1 = c.get(Calendar.YEAR);
       
        if( ( y2 < y1) || ( y1 == y2 && m2 < m1 ) || ( y1 == y2 && m1 == m2 && d2 <= d1) ) /*changed to <= for this Pregtool*/
            return true;    
        return false; 
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
   
     
    public void showAbout()
    {
        this.get_textForm().setTitle("About");
        this.get_textItem().setText("Pregtool Version 1.0 Copyright (c) 2007 Kasidit Yusuf. All rights reserved. This software is FREE! Please help the poor, the orphans and spend in charity! Please do good and stay away from bad deeds! May God Guide & Bless. Special Thanks to Dr. Selvio Simon (Brazil) for his great ideas and suggestions for this project.\n DISCLAIMER: This application is provided AS IS. No warranties whatsoever are implied.\n This version of Pregtool is FREE, you may freely distribute and use it.\n NOTE: This program is compatible with the Gregorian calendar only.");            
        Display.getDisplay(this).setCurrent(get_textForm());        
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
}
