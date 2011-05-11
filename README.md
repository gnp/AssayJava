# README

**AssayJava** contains code you can use to perform &ldquo;assays&rdquo; of data sets from databases
or flat files or other data sources to help you understand the variability of the
data.

I have used this to validate what people told me about what was *supposedly* in the
data set they were going to have me depend on. I would get the data file from them,
run an assay and then ask them all the follow up questions like &ldquo;wait, what does
it mean when the `order_date` field contains the text &lsquo;`X`&rsquo; instead of a date?&rdquo;

Here&rsquo;s a simple example code fragment from one of the unit tests. It sets up an
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

Here&ldquo;s an example of a subset of the report output, for one field of a data set, in this case an element named &ldquo;Longitude&rdquo;. **AssayJava** catches some oddities in the data that need to be considered if this data is going to be processed. To wit: (1) The non-numeric values &lsquo;N&rsquo; and &lsquo;S&rsquo; appear; and (2) some rows have no value (an empty string) for this data element.

```
Longitude [ specified type TEXT ]:

  PATTERN                         VALUE                                     DISTINCT    OCCURS
  ------------------------------  ----------------------------------------  --------  --------
  BOOLEAN:FALSE                   "N"                                              1        11
  CHAR(   1)                      "S"                                              1       212
  DECIMAL( 4,  1)                 "-109.2"                                         1         7
  DECIMAL( 4,  1)                 "-109.4"                                         1         1
  DECIMAL( 5,  2)                 *                                               19        52
  DECIMAL( 6,  3)                 *                                               23        39
  DECIMAL( 7,  4)                 *                                              229       368
  DECIMAL( 8,  5)                 *                                            2,028     3,308
  DECIMAL( 9,  6)                 "-109.356162"                                    1         4
  DECIMAL( 9,  6)                 "-109.401376"                                    1         1
  DECIMAL( 9,  6)                 "-109.401995"                                    1        10
  DECIMAL( 9,  6)                 "-111.263029"                                    1         1
  DECIMAL( 9,  6)                 "-111.263452"                                    1         1
  DECIMAL( 9,  6)                 "-111.263658"                                    1         1
  DECIMAL( 9,  6)                 "-111.264708"                                    1         1
  DECIMAL( 9,  6)                 "-111.264788"                                    1         1
  DECIMAL( 9,  6)                 "-111.296844"                                    1         1
  DECIMAL( 9,  7)                 "38.9418491"                                     1         2
  DECIMAL( 9,  7)                 "39.1566592"                                     1         5
  DECIMAL(10,  7)                 *                                               15        56
  EMPTY                           ""                                               1     1,585
  INTEGER( 7)                     "-1103610"                                       1         2
  INTEGER( 7)                     "-1104910"                                       1         1
  INTEGER( 8)                     "-10969003"                                      1         6
  INTEGER( 8)                     "-10969467"                                      1         8
  ------------------------------  ----------------------------------------  --------  --------
                                                                               2,334     5,684
```