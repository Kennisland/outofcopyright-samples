using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Data;
using System.Data.OleDb;
using System.Collections;

namespace APITest
{
    class excel
    {
        public static DataRowCollection getContent(string file)
        {
            OleDbConnection oCnx = null;
            string sCnxString = "Provider=Microsoft.Jet.OLEDB.4.0;Data Source=" + file + ";Extended Properties=\"Excel 8.0;HDR=Yes;IMEX=1\"";
            oCnx = new OleDbConnection(sCnxString);
            oCnx.Open();

            DataTable ExcelSheets = oCnx.GetOleDbSchemaTable(System.Data.OleDb.OleDbSchemaGuid.Tables, new object[] { null, null, null, "TABLE" });
            string SpreadSheetName = "[" + ExcelSheets.Rows[0]["TABLE_NAME"].ToString() + "]";

            // on peut ensuite récupérer le contenu de la table avec
            string sCmdString = "select * from " + SpreadSheetName;
            OleDbCommand oCmd = new OleDbCommand(sCmdString, oCnx);
            OleDbDataAdapter oAd = new OleDbDataAdapter();
            DataSet oDs = new DataSet("ExcelDataset");
            oAd.SelectCommand = oCmd;

            oAd.Fill(oDs);
            return oDs.Tables[0].Rows;
        }
    }
}
