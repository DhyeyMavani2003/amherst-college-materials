**1. Suppose Huffman coding is used to compress a text file containing 8 million characters, so that the original file size is 8 MB (= 8 million bytes). (Each character is encoded using 1 Byte = 8 bits.) What is the smallest possible size of a Huffman encoding that could result from the file? What orignal file(s) would generate an encoding of minimal size?**

Answer:
Smallest possible size of a Huffman encoding is (size/8 bytes = ) 1 MB  in the case when the original file is having a single character being repeated 8 million times.


**2. Will the Huffman encoding always generate a smaller document than the original document? Could the Huffman encoding result in a larger encoded file? For what docuemnts would you expect a Huffman encoding to result in the largest encoded file?**

Answer: 
No. Let's say that we create a Huffman encoding from the shakespeare document and use it to encode a document that has a single character being repeated over and over (e.g. zzzzzzzzzz) then the original size would be 8, but the encoded size would be greater than 8. You may ask why. Here is why: let's say the huffman encoding generated for z is 0110101010, then the new size would be 100 bits (= 100/8 bytes) and the original size was 10 bytes, so we will have an encoded file which is larger than the actual file.


**3. The Huffman code for shakespeare.txt results in an encoding that is approximately 60% of the size of the original file. Suppose the encoding for shakespeare.txt is used to encode other document, some-text.txt. Would you expect a similar compression ratio (60%) for the resulting encoding? Why or why not? Under what conditions would you expect to see a similar compression ratio for some-text.txt?**

Answer: 
As shakespeare.txt is in old english, I can expect other old english texts related to shakespeare.txt to have similar compression ratio. Furthermore, I do not expect the compression ratio to be similar for other texts which have significantly different frequency distribution fo the characters used in the original text. Also, it is worthwhile to note that there is no possible file showing higher compression ratio than the shakespeare.txt because the intial encoding was generated from shakespeare.txt 