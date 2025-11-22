Android Studio 3.2.1 project for lejOS LEGO NXT in Mac OSX 10.12 environment. 

Application is used for controlling LEGO NXT Mindstorm 2.0 hub over Bluetooth (classic). LEGO LCP -protocol is used
for sending commands to hub. To be continued ...


Start Working with Local Workstation Git

1. On local machine go to Studio project folder
2. If not done, use:
   git init  to create .git -file (a repo is initialized)
3. Create .gitignore -file
4. Create recommented content for .gitinignore when using Android-project
5. Add files to repo:
   git add .   (add all)
6. Make first commit:
    git commit -m "Initial commit: Android Studio project"
7. Create a new repo in GitHub
    New ropository -> Name: proj_name -> Public/Private -> Create

8. Add GitHub repo as remote connection
   git remote add origin https://github.com/YOUR_USERNAME/nxtcontrol.git
9. Check Remote connection
    git remote -v
    Result ->  origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (fetch)
             origin  https://github.com/YOUR_USERNAME/nxtcontrol.git (push)


GITHUB Fine-Grained Token Notes

1. Remove old credentials in terminal in local machine project folder (nxtcontrol)
 
   git credential-osxkeychain erase
   host=github.com
   protocol=https

   makes sure Git does not use old password or token

  2. Create Fine-Grained token in GitHub

     go: https://github.com/settings/personal-access-tokens
     Choose: Fine-Grained -> Generate new token
     Give token name f.ex.: nxcontrol-terminal-pushpull-YYYYMMDD
     Resource owner: (your id)
     Permissions:  + Add permissions -> Contents -> Read & Write
     Expiration: choose (30/60/90 days)
     Push: 'Generate token' and copy the token (github_pat_....)

  3. Check Git remote in workstation

     git remote -v
     result: https://github.com/your_id/nxtcontrol.git
     if shows SSH, change to HTTPS
     git remote set-url origin https://github.com/mhatakka/nxtcontrol.git

  4. Set  credential helper for OSX (optional). Sets token to Keychain.
     Now you don't have to write tokens when Password is asked

     git config --global credential.helper osxkeychain

   5. Push files with Fine-Grained token to GitHub for the FIRST TIME (only)
      git push -u origin main
      Username: YOUR_USERNAME
      Password: (key)   (copy and past GitHub -token -> ENTER . Note! Key is not shown in terminal!!)

   6. Following push/pull -commands

       Because the credential helper has been installed, Git retrieves the token from the KeyChain automatically!
       git push
       (git push)

  Adding & Updating File(s)

  1. New file
     git add  <filename>   // only the file
     git add .             // all new files
  2. git status
     Changes to be committed:
  (  use "git reset HEAD <file>..." to unstage)

	    new file:   <filename>
  3. Make a commit
     git commit -m "Added a new file xxx"
  4. git push
     The new file is on GitHub

  1. Changed file content  example.txt
     git status
     Changed not staged for commit:
     (use "git add <file>..." to update what will be committed)
  2. git add example.txt
  3. git status
     Changes to be committed:
     (use "git reset HEAD <file>..." to unstage)

	   modified:   app/src/main/java/com/example/nxtcontrol/MainActivity.java
  5. git push
  
