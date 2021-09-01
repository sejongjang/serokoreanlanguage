#Sero

Language learning application

E-Book type language learning application, inspired by Google e-book service.

Application will provide elivated level learning metirial as an e-book with quiz, vocab and other study features.

Front End
Android platform using Java.

E-Book will be displayed as an Web Text(HTML) by using anroid webview.

It will be 5 different levels and each chapter will have 30 chapters. (select level to access to chapters)

Each chapter has chapter quiz.

Quiz will be desgined based on 'spaced repetition(https://en.wikipedia.org/wiki/Spaced_repetition)' to meximize memorization for nessassry vocab.

Back End
post: Back end will decide which data will be stored in database. Mostly quiz related data will be stored, because spaced repetition requires calculation based on data and frequency.

get: check and calculate priority based on the response from the batabase and return.

Database
NoSQL (have not decide which type will be proper yet, however, I think key-value type will be useful).

Store calculated quiz data as a key value pair of Chapter and the list of quiz.
