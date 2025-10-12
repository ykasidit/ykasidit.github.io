---
title: Windows C C++ - Autostart program on Boot of computer using the Registry
category: programming
---

An example for autostarting your software using the registry - I think it was inspired by <http://www.codeguru.com/cpp/w-p/system/registry/article.php/c5677> a long while back...

<pre>

	////////////auto-start

	HKEY hKey;
	CString sKeyName;
	unsigned char szFilePathOri[MAX_PATH];
	unsigned char szFilePath[MAX_PATH];

	GetModuleFileName(AfxGetInstanceHandle(), (char *)szFilePathOri, MAX_PATH);
	strcpy((char *)szFilePath,"\"");
	strcat((char *)szFilePath,(char *)szFilePathOri);
	strcat((char *)szFilePath,"\"");
	strcat((char *)szFilePath," /autostart");



	LONG lnRes = RegOpenKeyEx(
           HKEY_LOCAL_MACHINE,  // handle of open key
             // The following is the address of name of subkey to open
           "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run",
           0L,KEY_WRITE,
           &hKey            // address of handle of open key
       );

	// now add program path to the RUN key
	lstrcpy( (char *) szFilePath , LPCTSTR(szFilePath) );


	if( ERROR_SUCCESS == lnRes )
	{
		if(code == BST_CHECKED)
		{

		   lnRes = RegSetValueEx(hKey,
								 LPCTSTR( "my_program" ),  // handle of the opened
													   // key to set value for
								 0,
								 REG_SZ,
								 szFilePath,   //value data
								 MAX_PATH );

		   if(ERROR_SUCCESS == lnRes)
			   ;
		   else
		   {
			   AfxMessageBox("Auto-start on boot set failed");
			   mydebug::log("Auto-start on boot set failed");
		   }

		}
		else
		{
			 lnRes = RegDeleteValueA(hKey, LPCTSTR( "WMouseXP" ));

		   if(ERROR_SUCCESS == lnRes)
			   ;
		   else
		   {
			   AfxMessageBox("Auto-start on boot remove failed");
			   mydebug::log("Auto-start on boot remove failed");
		   }


		}

	}
	else
	{
		AfxMessageBox("Opening of auto-start on boot settings failed");
		mydebug::log("Opening of auto-start on boot settings failed");
	}


	//////////////
}
</pre>