require 'rexml/document'
require 'date.rb'
include REXML

files = Dir.glob "*.xml"

files.each do |filename|

file = File.new(filename)
doc = Document.new(file)

title = ""
subtitle = ""
body = ""
date = ""

doc.elements.each() do |p|
	p.elements.each("title") do |q|

	title = q[0]
	break	
	end
	p.elements.each("subtitle") do |q|

	subtitle = q[0]
	break	
	end
	p.elements.each("body") do |q|

	body = q[0]
	break	
	end
	p.elements.each("date") do |q|

	date = q[0]
	break	
	end

  end

puts filename
title = title.to_s
if(title != nil && title.length > 0)

	puts title
	puts date

	d = DateTime.parse date.to_s
	title =  title.to_s
	titleori = title.clone
	title.downcase!
	title = title.gsub " ","_"
	title = title.gsub "'",""
	title = title.gsub ".",""
	title = title.gsub "/","_"
	title = title.gsub ":",""
	newfn = "#{d.year}-#{d.month}-#{d.day}-#{title}.html"
	puts newfn

	outf = File.open(newfn,'w')
	outf.write "---
layout: post
title: #{titleori.gsub ":",""}
---
"
	outf.write(body)
	outf.close

end
end
