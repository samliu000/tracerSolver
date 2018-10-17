# tracerSolver

Sam this is a reminder of how to use git for you to read. 
======================================================================================================================
To initilize a local repo, open terminal and navigate to the folder you want to init the repo at by using "cd <path>" to move down a repo.
  Ex: 
  type: "cd MyFolder" to goto the folder named "MyFolder"
        "cd MyFolder\SecondaryFolder\Project" to go directly to a specific location(the path may not be in mac syntax)
        
The directory you are in is shown at the beginning of the line in terminal.

* After arriving at a desired location, usually the highest level folder in the project folder, type "git init" to create a new repotitory 
in that folder. A folder named ".git"should be created,though it may be hidden from you and you may need to change the mac settings to 
show hidden files.
* Alternatively you can type "git clone <url> <foldername(optional)>" to clone an existing repo. If no foldername is provided, a folder 
that has the same name as the repo you are cloning is created in that folder.
* If you created an empty repo and wish to download files manually, use "git remote add <name> <url>" to add a remote to your repo. A remote
is another repo that you can pull(copy) from and push(paste) to. The name of the remote can be arbitrary, but url has to be specific.The 
"git clone" command automatically sets up a remote that is set to default as "origin".
* To pull from that repo, type "git pull --set-upstream <name-of-remote> <name-of-local>" to pull. "name-of-remote" is the name you specified
previously, whereas "name-of-local" should be "master". This name can be changed, but I will not explain it here. This full command only 
has to be typed during the first pull. After the first time "git pull" will pull from the repo you setup the first time. 
* To push to a repo, type "git push". To destination of that push is the url you setup during pulling. ALWAYS PULL ONCE BEFORE PUSH. This is 
because the remote repo may contain different data from your local, always pulling first will reduce the times terminal will shout at you.
* Errors will occur, if they do, they are usually conflicts between files. Look up the error message or ask me to fix the error.
* Type "git status" to see what the status your repo is in. You will only need to look at the red and the green texts. Red texts tell you
what files are not staged(will be commited). By typing "git add <file1> <file2> <file3> ..." to stage the file. After staging type 
"git status" you will see these files are now in green.
* Type "git commit -m <message>" to commit changes. There must be a commit message or the commit will fail. The message is a string, thus
need to be surrounded by quotation marks. ONLY COMMITTED CHANGES WILL BE PUSHED. 
