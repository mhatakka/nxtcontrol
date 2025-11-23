READ ME

Android Studio 3.2.1 project for lejOS LEGO NXT in Mac OSX 10.12 environment. 

Application is used for controlling LEGO NXT Mindstorm 2.0 hub over Bluetooth (classic). LEGO LCP -protocol is used
for sending commands to hub. To be continued ...


Start Working with Git & GitHub

1. On local machine go to Studio project folder
2. If not done, use:
   - 'git init'  to create .git -file (a repo is initialized)
3. Create .gitignore -file: 'touch .gitignore'
4. Create recommented content for .gitinignore -file when using Android-project
5. Add files to repo:
   - 'git add .'   adds all files except .gitignore cases
6. Make first commit:
    - 'git commit -m "Initial commit: Android Studio project" '
7. Create a new repo in GitHub -site
    - New ropository -> Name: 'nxtcontrol' -> Public/Private -> Create

8. Add GitHub repo as remote connection on local machine
   - 'git remote add origin https://github.com/YOUR_USERNAME/nxtcontrol.git'
9. Check remote connection on local machine
    - 'git remote -v'
    - Result ->  origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (fetch)
             origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (push)


GITHUB Fine-Grained Token 

1. Remove old credentials from Keystore on local machine project folder (nxtcontrol)
 
   - 'git credential-osxkeychain erase'
   host=github.com
   protocol=https

   - This makes sure Git does not use old password or token

  2. Create Fine-Grained token in GitHub

     - go: https://github.com/settings/personal-access-tokens
     - Choose: Fine-Grained -> Generate new token
     - Give token name f.ex.: nxcontrol-terminal-pushpull-YYYYMMDD
     - Resource owner: (your id)
    -  Permissions: Use  '+ Add permissions' -button  -> Contents -> Read & Write
    - Expiration: choose (30/60/90 days)
    - Push: 'Generate token' and copy the token (github_pat_....)

  3. Check Git remote in workstation

     - 'git remote -v'
    - result: https://github.com/your_id/nxtcontrol.git
    -  if shows SSH, change to HTTPS
    -  'git remote set-url origin https://github.com/YOUR_USERNAME/nxtcontrol.git'

  4. Set  credential helper for OSX (optional). This sets token to OSX Keychain.
     Now you don't have to write tokens when Password is asked

    -  'git config --global credential.helper osxkeychain'
	- Use 'Utilities -> KeyStore' (Lisäohjelmat -> Avainnipun käyttö) to check 

   5. Push files with Fine-Grained token to GitHub for the FIRST TIME (only)
      - 'git push -u origin main'
     -  Username: YOUR_USERNAME
     -  Password: (key/token)   (copy and past GitHub -token -> ENTER . Note! Key is not shown in terminal!!)

   6. Following push/pull -commands is used without giving your credentials!

       - Because the credential helper has been installed, Git retrieves the token from the KeyChain automatically!
       - 'git push'   when sending data
       - ('git pull')  when retrieving data

  Adding & Updating File(s) on Local Machine

  1. ADD: When a New File Is Added to Local Repo
     - 'git add  <filename>'  add when only one file
     -  'git add .'           add  when all new files
     -  
  2. Check status
     - 'git status'
     - Result: Changes to be committed:
     -    (  use "git reset HEAD <file>..." to unstage)
     -    new file:   <filename>
	
  4. Make a commit
     - 'git commit -m "Added a new file xxx"' write a commit message

  5. Send to cloud GitHub
     - 'git push'  The new file is sent to GitHub

  1. CHANGED FILE CONTENT: Changed file content of 'example.txt' file
     - 'git status'
     -   GIT -> Changed not staged for commit:
     -    (use "git add <file>..." to update what will be committed)
          
  2. Add the changed file !!
     - 'git add example.txt'
     - 
  4. Chech status again
     - 'git status'
     - GIT:  Changes to be committed:
     - (use "git reset HEAD <file>..." to unstage)
     - modified:   <path>/example.txt
	
  5. Send to cloud GitHub
     - 'git push'
  
 File UPDATED on GitHub cloud: 'fetch' info and the use 'status' to show the un-committed files
 
    - 'git fetch'  gets the data but does NOT combine it/those to local branch
    -     remote: Enumerating objects: 8, done.
    -     remote: Counting objects: 100% (8/8), done.
    -     remote: Compressing objects: 100% (6/6), done.
    -     remote: Total 6 (delta 4), reused 0 (delta 0), pack-reused 0 (from 0)
    -     Unpacking objects: 100% (6/6), done.
    -     From https://github.com/mhatakka/nxtcontrol
	
    - 'git status'   shows the status of the branch(es)
    -     On branch main
    -     Your branch is behind 'origin/main' by 2 commits, and can be fast-forwarded.
    -     (use "git pull" to update your local branch)
	
    - 'git log ..origin/main --oneline' shows NEW commits on GitHUB repo
    -     a81d8c1 Update README.md again
    -     a4ea728 Update README.md
	
    - 'git log origin/main.. --oneline' shows NEW commits on LOCAL repo (RESULT may be empty, use 'git status')
	
    - 'git diff origin/main' show differences on files between GitHub and local repo
    -      diff --git a/README.md b/README.md
    -      index 8c436c6..5d2675d 100644
    -       --- a/README.md
    -        +++ b/README.md
    -        @@ -6,106 +6,95 @@ Appl ... etc
	
    -   'git fetch origin'  For checking the changes. ('origin' is ALIAS for remote repo name.) Loading the changes WITHOUT seeing/doing the merge
    -    and then use 'git diff origin/main' to see the differences. NOTHING has changes.
	
    -   'git pull'   does fetch + merge. The file content is UPDATED!

   Cloning the GitHub Repository
   - 'git clone https://github.com/mhatakka/nxtcontrol.git' 

   Meaning of 'origin'
    - origin = https://github.com/mhatakka/nxtcontrol.git
   - origin/main  == GitHub repo branch   
   - main         == local repo branch
   - 'git fetch'  reads data from ALL remote repos which have been configired (origin, upstream, backup)
   - 'git fetch origin' reads ONLY from GitHub-repo

  Meaning of 'upstream'
  - upstream: "What is the remote branch, which is followed by the local branch?", "Where does this branch come, to what is it compared to?"
  - Local branch: main
  - Upstream brach: origin/main
  - 'git branch --set-upstream-to=origin/main main'  git saves this (upstream)
  - When 'upstream' is set:
  - 'git status' shows if branch is "behind", "ahead"  or "diverged"
  - 'git pull' knows where to get the changes
  - 'git push' knows into which branch to send the changes

    'Upstream' = Remote repo, from which the copy of a project (fork) has been done
    - When copying the project of another user, your own remote (forked) project repo is still 'origin'
    - The original (of another user) project repo  is 'upstream'
    - 'git remote add upstream https://github.com/USERID/original-repo.git'  makes a 'path' to the original repo
    - 'git fetch upstream'
    -  'git merge upstream/main'

    Simulation 'forking' project on local machine
    - Create a project 'git_test' on  local machine and GitHub on 'git_test'-folder
    - Create another folder 'fork_git_test' and move into that (cd ..; mkdir fork_gittest; cd for_git_test)
    - Use 'git clone https://github.com/YOUR_USERNAME/git_test.git'
    - Now you have two diffrent local repositories. Both pointing to same 'origin' (GitHub)
    - NOTE! There might to appear very easily cases where
    -     ! [rejected] main -> main (fetch first)
    - SOLUTION:  use always 'git pull --rebase' before 'git push'

    


  
