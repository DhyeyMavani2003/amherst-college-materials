1. How does the performance of LinkedSimpleUSet and MTFSimpleUSet compare for the task of computing the number of distinct words in the texts you tested? Does the move-to-front heuristic seem to lead to a significant improvement? (Please include a table and/or graph of performance to support your answer.)

**I have included the graph and table in the submission zip file. According to the data generated based off the word counts and different implementations in the given text files, LinkedSimpleUSet turns out to be better than that of MTFSimpleUSet. Although, the difference is not that significant.**


2. When using a SimpleUSet to count the number of distinct words in a text, under what conditions would you expect the move-to-front heuristic to give a significant performance increase? Would you expect (most) English language texts to satisfy some of these conditions?
**In my opinion, the move-to-front heuristic will give better results, in terms of significant performance increase, in the case of texts when there are a lot of concurrent repeating words. I would not expect most english language texts to satisfy this condition.**


3. Under what conditions would you expect the move-to-front heuristic not to give any significant performance improvement? Can you describe a text file for which you’d expect MTFSimpleUSet to be significantly less efficient than LinkedSimpleUSet?

**In a text file where there are a lot of different words, and where there are very few concurrent repetitions of words, the move-to-front heuristic will not give any significant performance improvement. A text file which lists all the words of a dictionary will lead the MTFSimpleUSet implementation to be significantly less efficient than LinkedSimpleUset implementation. We can especially see this case in the result in the included graphs and chart in case of the EnglishWords.txt file.**

**Also, the move-to-front heuristic will be less efficient when we have repititions of words which a placed far apart but at equal intervals of number of words betwwen them. You may visualize this in terms of musical notes or poems, where the MTFSimpleUSet implementation will not be effective as it will be wasting time juggling adding/replacing layers of same set of words**