Set objExcel = CreateObject("Excel.Application")
Set objWorkbook = objExcel.Workbooks.Open("C:\BSMRECEIPT.xlsm")

objExcel.Application.Run "BSMRECEIPT.xlsm!ImportCsv" 
objExcel.Application.Run "BSMRECEIPT.xlsm!PrintPDF"
objExcel.ActiveWorkbook.Close

objExcel.Application.Quit
WScript.Quit