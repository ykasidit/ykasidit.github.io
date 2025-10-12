---
title: Programmatially Send files using OBEX Object Push over the Microsoft Bluetooth Stack
category: programming
---

A while back during my senior project of my BSc computer science studies, I wrote an implementation of parts_of_HCI, L2CAP, SDP, RFCOMM and OBEX on top of freebt.net's bluetooth implementation for Windows.

That same OBEX code was modified to be used with the MS BT stack to send files over bluetooth - please see the code of WMouseXP open-source version, the fuction "sendFile" at <https://github.com/ykasidit/WMouseXP/blob/master/src/WMouseXPDlg.cpp> and the main OBEX layer packet code was <https://github.com/ykasidit/WMouseXP/blob/master/src/OBEX.cpp> - sorry for the low quality/cleanliness of code here - it was written when I was still just starting C and C++ programming back then - but it worked when tested with many Bluetooth devices/phones.

The main part is making the OBEX object:
<pre>
bool CWMouseXPDlg::SendFile(char* devaddr, int port, BYTE* dataptr, DWORD datalen)
{
	//close socket to make sure it is closed, new session
	closesocket(iSocket);    
	WSACleanup();

	if(!InitSocket())
	{
		throw "Initialize MS Bluetooth stack/driver failed";		
	}

	SOCKADDR_BTH sa = { 0 };
	int sa_len = sizeof(sa);


	if( SOCKET_ERROR == WSAStringToAddress( devaddr, AF_BTH,
	NULL, (LPSOCKADDR) &sa, &sa_len ) ) 
	{
		throw "Convert BT address WSAStringToAddress failed";	
	}

	sa.port = port;

	if( sa.port == 0 ) {
		iStep1Label.SetWindowText("Invalid device OBEX port specified");
	return false;
	}	




	//open socket



	//Made new send and recv functions implements a timer, see SendViaSocketL and RevcViaSocketL

	//try catch handles when send and recv fails






	//make safe cleanup when exceptions occur



	OBEXPutCommand* opc = NULL;

	OBEXPacket* obexPktToSend = NULL; //file part packet


	bool success = false;

	try{
              
				iStep1Label.SetWindowText("Waiting for phone to accept file...");

				StartBTInteractionTimer(); //so we don't wait too long for a user to respond

				if(SOCKET_ERROR != connect( iSocket, (LPSOCKADDR) &sa, sa_len ))
                  {
						//Send OBEX Connect				

						StopBTInteractionTimer();  	

						m_InstallPic.Draw();

					    OBEXConnectPacket obexConnectPkt(Opcode_Connect,0x4000);				

                        BYTE recvBuff[512];												

						SendViaSocketL((const char*) (obexConnectPkt.wholePacket),obexConnectPkt.packetLength);


						bool startedPut	= false;
						USHORT theirMaxOBEXPacketSize = 0;					


						//See response, then Send all OBEX file packets											

						int nreceived = RecvViaSocketL((char*)recvBuff,512);						



                        if(recvBuff[0] == RspCode_Success)
                        {
                              	
							iStep1Label.SetWindowText("Connect OBEX Success");		


						while(1)
						{

							if(startedPut)
								nreceived = RecvViaSocketL((char*)recvBuff,512);


							OBEXPacket obexRcvdPkt(recvBuff,nreceived);


							if(!startedPut)
							{

									if(obexRcvdPkt.wholePacket->code == RspCode_Success)
									{
										iStep1Label.SetWindowText("OBEX connection successful");

										BEUS tmo(((BYTE*)(obexRcvdPkt.wholePacket->data))+2);					
										theirMaxOBEXPacketSize = tmo.getValUSHORT(); 

										char buf[50] = {0};
										sprintf(buf,"Their max OBEX packet size: %d",theirMaxOBEXPacketSize);
										iStep1Label.SetWindowText(buf);



										//file->Open("card.vcf",CFile::modeRead|CFile::typeBinary);
										//file->Open("img.jpg",CFile::modeRead|CFile::typeBinary);
										//if(opc)
										//	delete opc;

										 opc = new OBEXPutCommand("WMouseXP.jar",theirMaxOBEXPacketSize,dataptr,datalen);							

										//sprintf(buf,"N OBEX puts to send: %d",opc->nPacketsToSend);
										iStep1Label.SetWindowText("Sending File...");
										//devicesLB->DrawAnimatedRects()


													obexPktToSend = opc->CgetNextPacket();

													SendViaSocketL((const char*) (obexPktToSend->wholePacket),obexPktToSend->packetLength);

													delete obexPktToSend;
													obexPktToSend = NULL;

													startedPut = true;

									}


							}						
							else
							{
									if(obexRcvdPkt.wholePacket->code == RspCode_Success)
										{
											//send obex disc
											iStep1Label.SetWindowText("File sent via Bluetooth");
											success = true;

											OBEXPacket dsc(Opcode_Disconnect,NULL,0);

											//AddLog("Sending OBEX Disconnect Request");
										    SendViaSocketL((const char*) (dsc.wholePacket),dsc.packetLength);					
											//AddLog("OBEX Disconnect Sent");					
											iStep1Label.SetWindowText("File sent via Bluetooth");
											break;

										}
										else
									if(obexRcvdPkt.wholePacket->code == RspCode_Continue)
										{
											obexPktToSend = opc->CgetNextPacket();	

											SendViaSocketL((const char*) (obexPktToSend->wholePacket), obexPktToSend->packetLength);

											delete obexPktToSend;
											obexPktToSend = NULL;//avoid double deletion

										}
									else
										{
											throw "Unknown OBEX Response";
											//break; throw already broke...
										}


							}//end else of if(!started)


							}//end while	




						}//end if(recvBuff[0] == RspCode_Success)			
						else
						{
							throw("OBEX handshake failed.");							

						}	
                        

				}// end if(connect!=SOCKET_ERROR)                                          
				else
      			{			StopBTInteractionTimer();
							throw "Connection Declined or Timed-Out";
				}





	}
	catch(char* exception)
	{
		iStep1Label.SetWindowText(exception);
	}


	delete obexPktToSend;
	delete opc;


	closesocket(iSocket);      
	WSACleanup();

	return success;

}
</pre>