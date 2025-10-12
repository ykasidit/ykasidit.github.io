---
title: Android NDK Get IMSI from C code
category: programming
---

Although not really "native", but works well by parsing a command line call instead - credit goes to "Alex P." for the answer in <http://stackoverflow.com/questions/14813875/how-to-get-imsi-number-in-android-using-command-line> - thanks for the command.

<pre>

char cmd_res_line[256];
char total_cmd_res[25600];


//WARNING! NO HANDLING FOR VERY_LONG COMMAND READS YET
char* exec_get_out(char* cmd) {

    FILE* pipe = popen(cmd, "r");

    if (!pipe) 
      return NULL;

    total_cmd_res[0] = 0;

    while(!feof(pipe)) {
        if(fgets(cmd_res_line, 256, pipe) != NULL)
	  {
	    //TODO: add handling for long command reads...
	    strcat(total_cmd_res,cmd_res_line);
	  }
    }
    pclose(pipe);
    return total_cmd_res;
}

...

char imsi[16];
imsi[15]=0;
char* res = exec_get_out("service call iphonesubinfo 3");  
if (strlen(res)>210) {
	imsi[0] = res[75];
	imsi[1] = res[77];
	imsi[2] = res[79];
	imsi[3] = res[81];
	imsi[4] = res[136];
	imsi[5] = res[138];
	imsi[6] = res[140];
	imsi[7] = res[142];
	imsi[8] = res[144];
	imsi[9] = res[146];
	imsi[10] = res[148];
	imsi[11] = res[150];
	imsi[12] = res[205];
	imsi[13] = res[207];
	imsi[14] = res[209];
	printf("imsi: %s len %d\r\n",imsi,strlen(imsi));
}
</pre>