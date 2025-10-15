#!/usr/bin/env ruby

require 'date.rb'

d = DateTime::now
puts "title:"
title = gets.strip
puts "category:"
cg = gets.strip


if(title != nil && title.length > 0)
	titleori = title.clone
	title.downcase!
	title = title.gsub " ","_"
	title = title.gsub "'",""
	title = title.gsub ".",""
	title = title.gsub "/","_"
	title = title.gsub ":",""
	newfn = "#{d.year}-#{d.month}-#{d.day}-#{title}.markdown"
	puts newfn

	fp = "_posts/#{newfn}"
	outf = File.open(fp,'w')
	outf.write "---
layout: post
title: #{titleori.gsub ":",""}
category: #{cg}
---
"
	outf.close
	system("emacs #{fp}")

end
