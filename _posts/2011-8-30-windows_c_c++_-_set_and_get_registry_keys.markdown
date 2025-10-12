---
title: Windows C C++ - Set and Get Registry Keys
category: programming
---

Used this code a while back to Modify: Set and Get the Reigstry keys.

<pre>
static
BOOL SetRegistryKey (
  char* keyPath,
  char* keyName,
  char* keyData
) {
  HKEY  key;
  long  error;
  DWORD disposition;

  error = RegCreateKeyEx(
    HKEY_CURRENT_USER, keyPath, 0, NULL, REG_OPTION_NON_VOLATILE,
    KEY_ALL_ACCESS, NULL, &key, &disposition
  );
  if ( error ) return FALSE;

  error = RegSetValueEx(
    key, keyName, 0, REG_SZ, (BYTE*) keyData, lstrlen(keyData) + 1
  );
  RegCloseKey(key);
  if ( error ) return FALSE;

  return TRUE;
}

//////////////////////////////////////////////////

static
BOOL GetRegistryKey (
  char* keyPath,
  char* keyName,
  char* keyData
) {
  HKEY  key;
  long  error;
  char  content[1024];
  DWORD type = REG_SZ;
  DWORD size = 1024;

  error = RegOpenKeyEx(
    HKEY_CURRENT_USER, keyPath, 0, KEY_ALL_ACCESS, &key
  );
  if ( error ) return FALSE;

  error = RegQueryValueEx(
    key, keyName, NULL, &type, (BYTE*) content, &size
  );
  RegCloseKey(key);
  if ( error ) return FALSE;

  lstrcpy(keyData, content);
  return TRUE;
}

</pre>