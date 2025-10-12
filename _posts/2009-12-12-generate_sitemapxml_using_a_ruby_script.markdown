---
title: Generate sitemap.xml using a ruby script
category: programming
---

As I'm using nanoc and jekyll together with git to edit and publish my site and blog, I was trying to generate sitemap.xml according to <http://www.sitemaps.org/> for [google webmaster tools](https://www.google.com/webmasters/tools/) sitemap settings.

So I wrote this ruby program: [gen_sitemap.rb](http://www.clearevo.com/gen_sitemap.rb) and called it from the git post_update hook on the server.

You can test it like `ruby gen_sitemap.rb "."` - add (multiple) folders as parametes.

In the script, remember to set `$site_url` to your own site.

Hope this is useful for your sitemap.xml needs!
