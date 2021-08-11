package com.company.depart.app.convertxlsxtocsv;

/**
 *
 * @author timsebring
 */
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ConvertXLSXtoCSV {

    private static String inputfilename = "";
    private static String outputfilename = "";
    private static int sheetindex = 0;
    
    static int counter = 1;  // Used to iterate over the cells in a row to determine when not to add a comma on the end (ie after last cell)
    
    public static void main(String[] args) throws Exception {
    	if (args.length != 3) {
    		throw new Exception("Requires 3 parameters. Usage: ConvertXLSXtoCSV <inputfile.xlsx> <outputfile.csv> <index of sheet to convert, starting at 0>");
    	}
    	inputfilename = args[0];
        outputfilename = args[1];
        sheetindex = Integer.valueOf(args[2]); // Convert to integer
        // Create the inputstream and workbook to read the original xlsx file
    	InputStream inp = new FileInputStream(inputfilename);
        Workbook wb = WorkbookFactory.create(inp);
        // Open the specified sheet
        Sheet inputsheet = wb.getSheetAt(sheetindex);
        //Prepare to write to the new file
        PrintWriter writer = new PrintWriter(outputfilename, "UTF-8");
        
        // The first (data) row of the spreadsheet contains header information, so they need to
        // be read as strings. The following rows have numeric values (NAIC rating, etc)

        // Get the first row that contains data. (0-based index)
        int firstRow = inputsheet.getFirstRowNum();
        // Get the last row, so we can iterate from first to last
        int lastRow  = inputsheet.getLastRowNum();
        int rowCount = firstRow;   // starting at the first row

        // firstCount contains the row with the headers, so we read that first
        Row headerRow = inputsheet.getRow(firstRow);
        Cell cell1 = headerRow.getCell(0);
        Cell cell2 = headerRow.getCell(1);
        Cell cell3 = headerRow.getCell(2);
        Cell cell4 = headerRow.getCell(3);
        
        // Write the header to the new file
        writer.print(cell1 + "," + cell2 + "," + cell3 + "," + cell4 + "\n");
       
        rowCount += 1;   //Increment the rowCount since we've already read headers
       
        // Read the rest of the rows in the sheet and print to the new csv file
        while (rowCount <= lastRow) {  
            Row row = inputsheet.getRow(rowCount);
            cell1 = row.getCell(0);
            writer.print(cell1 + ",");   // Every cell that's not the last in the row needs a comma afterward
            cell2 = row.getCell(1);
            writer.print(cell2 + ",");
            cell3 = row.getCell(2);
            writer.print((int)Math.round(cell3.getNumericCellValue()) + ",");
            cell4 = row.getCell(3);  // Contains a date -- need to keep MM/DD/YYYY format, else it auto-converts to DD-Mon-YY
            Date olddate = cell4.getDateCellValue();
            String mydate = new SimpleDateFormat("M/dd/yyyy").format(olddate);
            writer.print(mydate);
            rowCount += 1;
            // print the newline at the end of the row.
            writer.print("\n");
        }//End row loop
        writer.close();
    }
}