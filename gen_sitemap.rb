#!/usr/bin/env ruby

# gen_sitemap - use in git post-update hook to generate sitemap.org compat sitemap after each site update - written by Kasidit Yusuf <kasidit[AT]clearevo.com>

#thanks to http://www.mustap.com/rubyzone_post_162_recursive-directory-listing
#thanks to http://ruby.about.com/od/rubyfeatures/a/argv.htm

require 'find'

$site_url = "http://www.clearevo.com" #site url - without trailing /

puts"<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">"

if ARGV.size == 0
puts "please enter folder paths as params"
exit
end

ARGV.each do|param|
folder_path =  param.to_s
	Find.find("#{folder_path}") do |file|
		if file=~/.html$/ && !(file =~/thankyou_/) && !(file =~/google/)
			mod_date = File.ctime(file)
			file = file.gsub"./",""
			puts"
			<url>
			<loc>#{$site_url}/#{file}</loc>
			<lastmod>#{mod_date.strftime("%Y-%m-%d")}</lastmod>
			</url>"
		end #end if

	end #end for
end #end argc list for

puts "</urlset>"
