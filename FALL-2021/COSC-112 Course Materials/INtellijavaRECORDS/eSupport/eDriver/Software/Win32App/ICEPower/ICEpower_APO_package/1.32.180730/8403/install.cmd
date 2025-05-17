pnputil -a ./oemxaudioexticesoundapo/OemXAudioExtICEsoundAPO.inf /install
pnputil -a ./icesoundapo64/ICEsoundAPO64.inf /install
REG ADD HKLM\Software\ASUS\ICEpower_APO /v "DisplayVersion" /t REG_SZ /d 1.32.180730
