using System;
using System.Text;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Threading.Tasks;
using System.IO;
using System.Data;
using System.Collections;
using System.Json;

namespace APITest
{
    class Program
    {
        static void Main(string[] args)
        {
            RunAsync().Wait();
        }
        static async Task RunAsync()
        {
            using (var client = new HttpClient())
            {
                //Read data on excel
                DataRowCollection drc1 =  excel.getContent("data.xls");

                ArrayList list = new ArrayList();
                //Test with the belgium country and type of work Phonogram
                client.BaseAddress = new Uri("http://api.outofcopyright.eu/Belgium/Phonogram");
                client.DefaultRequestHeaders.Accept.Clear();
                client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                foreach (DataRow element in drc1)
                {
                    //Skip header
                    if (element.ItemArray[1] != "PUBLICATION_DATE")
                    {
                        //Post data
                        String data = "PUBLICATION_DATE=" + element.ItemArray[1] + "&CREATION_DATE=" + element.ItemArray[2] + "&COMMUNICATION_DATE=" + element.ItemArray[3];
                        StringContent queryString = new StringContent(data, Encoding.UTF8, "application/x-www-form-urlencoded");
                        //Send post data
                        HttpResponseMessage response = await client.PostAsync("",queryString);
                        if (response.IsSuccessStatusCode)
                        {
                            Stream JsonResponse = await response.Content.ReadAsStreamAsync();
                            JsonObject result = (JsonObject)JsonObject.Load(JsonResponse);
                            //Get result message
                            string str = (string)result["result"];
                            Console.WriteLine(element.ItemArray[0]+" Result : " + str);
                            
                        }
                    }
                    
                }
                //Stop console for read result
                Console.ReadLine();
            }
        }
    }
}
