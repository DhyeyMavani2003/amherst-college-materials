**1. Compare the preformance of your random permutation generator using a SkipList and LinkedSimpleList for a range of permutation sizes (i.e., choices of n). What is the largest permutation you can generate in approximately 1 second using the LinkedSimpleList? What about for the SkipList?**

Answer: 

The performance times scale almost linearly with n. The LinkedSimpleList is slower than the SkipList as you can see the slope of SkipList implementation is lesser than the LinkedSimpleList implementation according to the submitted graph of time taken v/s n.

In one second, we can add approximately 26000 elements in case of SkipList. 

In one second, we can add approximately 19000 elements in case of LinkedSimpleList.

**2. A professor decides that students will grade each others’ quizzes in class. To accomplish this, the students hand their quizzes to the professor, who procedes to shuffle the papers in random order. Each student is then given the quiz of a random student in the class. Soon, the professor realizes a problem with this method: there is nothing preventing a student from being handed their own quiz to grade! Apply your random permutation generator to answer the following questions:**

**a) If there are 10 students in the class, what is the approximate likelihood that some student is handed their own quiz to grade? What if there 100 students in class? 1,000 students? (Perform many trials to estimate the proportion of trials in which some student receives their own quiz.)**

Answer: 

Averaging over 500 trials, I got to know:
1. For 10 students, the likelihood that some student is handed their own quiz to grade is 0.542
2. For 100 students, the likelihood that some student is handed their own quiz to grade is 0.624
3. For 1000 students, the likelihood that some student is handed their own quiz to grade is 0.638




**b) If there are {10, 100, 1000} students in class, approximately how many students will on average receive their own quiz? (Again, perform many trials and count the number of students who receive their own quiz in each trial.)**

Answer: 

Averaging over 5000 trials, I got to know:
1. For 10 students, the average number of students that got handed their own quiz to grade is 0.761
2. For 100 students, the likelihood that some student is handed their own quiz to grade is 0.971 
3. For 1000 students, the likelihood that some student is handed their own quiz to grade is 1.116

**(A permutation of 1,2,…,n in which no number i appears in position i in the shuffled list is called a derangement. The first part of question 2 is equivalent to estimating the probability that a random permutation is a derangement.)**

