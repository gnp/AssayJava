# README

AssayJava contains code you can use to perform "assays" of data sets from databases
or flat files or other data sources to help you understand the variability of the
data.

I have used this to validate what people told me about what was *supposedly* in the
data set they were going to have me depend on. I would get the data file from them
and run this and then ask them all the follow up questions like "wait, what does
it mean when the order_date field contains the text 'X' instead of a date?".

Here's a simple example code fragment from one of the unit tests. It sets up an
input source to read from a tab-separated values text stream and outputs the assay
results to the console:

```java
final FileInputStream fis = new FileInputStream("src/test/data/ucrcfullcatalogue_13Jan09.txt.gz");
final GZIPInputStream gis = new GZIPInputStream(fis);
final InputStreamReader r = new InputStreamReader(gis);
final BufferedReader br = new BufferedReader(r);
final TabSeparatedValuesInput i = new TabSeparatedValuesInput();
i.setBufferedReader(br);
    
final ConsoleOutput o = new ConsoleOutput();
    
final Assayer assayer = new Assayer();
assayer.setInput(i);
assayer.setOutput(o);
assayer.run();
```
