---
title: Blogging via a text-editor and Git with jekyll
category: programming
---

Share the beneficial knowledge, tips, howtos with least effort. Write a text file (in markdown or textile) with your favorite text editor (gedit, emacs, vim, etc) then use git to commit and push it to your server. [Jekyll blog generator](http://wiki.github.com/mojombo/jekyll/) provides that possibility.

After using [Git](http://git-scm.com/) on [Ubuntu](http://www.ubuntu.com) happily for quite a while, I was really happy to write and maintian my whole site with [nanoc](http://nanoc.stoneship.org/) using markdown, I was still using pebble as my blog server and I searched quite a while for something like nanoc that was built for a blog, finally I found Jekyll. It just requires that you run its command in a folder with the right content - get an [example site](http://wiki.github.com/mojombo/jekyll/sites) source then extract it in a folder then run jekyll, it would generate the blog in the `_site` folder. All the rest of the info is there at [Jekyll project on github](http://wiki.github.com/mojombo/jekyll). 

A few things worth sharing:
- I dont know if this is unique to lighttpd or not, but links to folders that start with a / wont work so I needed to change all occurances of its [liquid template](http://wiki.github.com/tobi/liquid/liquid-for-designers) post.url to `post.url | remove_first:'/'`
- use full path to the css in the default.html in the layout - I'm using [Tom's template](http://github.com/mojombo/mojombo.github.com)
- The layout's post.html "related posts" generated links were broken, I needed to prefix its url with ../../../../ (four steps since I'm using categories too).
- I wrote/used a [little ruby program](http://www.clearevo.com/blog/files/pbj.rb) to migrate all my old blog entries from [pebble](http://www.pebble.sourceforge.net) xml content files.

For the git pull post-update hook that worked on a Ubuntu server, here's the script:

	#!/bin/bash
	#SHELL=/bin/bash
	#PATH=/sbin:/bin:/usr/sbin:/usr/bin
	#MAILTO=root
	#HOME=/
	unset GIT_DIR && cd /YourFolderWhichYouGitCloneLocally && git pull

(thanks to <http://www.taknado.com/en/2009/03/26/deploying-a-jekyll-generated-site/> for the `unset GIT_DIR` fix)

For info about how to setup git on your server see [this article on scie.nti.st](http://scie.nti.st/2007/11/14/hosting-git-repositories-the-easy-and-secure-way).

To sum it up, I really think jekyll is a great contribution to the world - as it makes sharing good knowledge via blogging much easier so, hopefully, more good knowledge can be shared for the benefit of mankind. Much thanks to the developers of Jekyll and Git!


