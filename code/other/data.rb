# frozen_string_literal: true

require_relative 'read_write.rb'

def print_stats
  all_attempts = read_attempts
  easy_attempts_wpm = []
  easy_attempts_accuracy = []
  decent_attempts_wpm = []
  decent_attempts_accuracy = []
  hard_attempts_wpm = []
  hard_attempts_accuracy = []

  read_attempts.each do |attempt_hash|
    case attempt_hash[:Complexity]
    when 1
      easy_attempts_wpm << attempt_hash[:wpm]
      easy_attempts_accuracy << attempt_hash[:Accuracy]
    when 2
      decent_attempts_wpm << attempt_hash[:wpm]
      decent_attempts_accuracy << attempt_hash[:Accuracy]
    when 3
      hard_attempts_wpm << attempt_hash[:wpm]
      hard_attempts_accuracy << attempt_hash[:Accuracy]
    end
  end

  puts "WPM High Score: #{read_high_score_wpm} \nStats: \n"

  unless easy_attempts_wpm.empty?
    calculate_shared_variables(easy_attempts_wpm)
    puts "For easy complexity, \nWords Per Minute: \n\tMean: #{calculate_mean}wpm, Median: #{calculate_median(easy_attempts_wpm)}wpm, Trend: #{calculate_trendline}wpm per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}"

    calculate_shared_variables(easy_attempts_accuracy)
    puts "Accuracy: \n\tMean: #{calculate_mean}%, Median: #{calculate_median(easy_attempts_accuracy)}%, Trend: #{calculate_trendline}% per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}\n"
  end

  unless decent_attempts_wpm.empty?
    calculate_shared_variables(decent_attempts_wpm)
    puts "For decent complexity, \nWords Per Minute: \n\tMean: #{calculate_mean}wpm, Median: #{calculate_median(decent_attempts_wpm)}wpm, Trend: #{calculate_trendline}wpm per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}"

    calculate_shared_variables(decent_attempts_accuracy)
    puts "Accuracy: \n\tMean: #{calculate_mean}%, Median: #{calculate_median(decent_attempts_accuracy)}%, Trend: #{calculate_trendline}% per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}\n"
  end

  unless hard_attempts_wpm.empty?
    calculate_shared_variables(hard_attempts_wpm)
    puts "For hard complexity, \nWords Per Minute: \n\tMean: #{calculate_mean}wpm, Median: #{calculate_median(hard_attempts_wpm)}wpm, Trend: #{calculate_trendline}wpm per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}"

  calculate_shared_variables(hard_attempts_accuracy)
  puts "Accuracy: \n\tMean: #{calculate_mean}%, Median: #{calculate_median(decent_attempts_accuracy)}%, Trend: #{calculate_trendline}% per attempt, Trendline Accuracy: #{calculate_trendline_accuracy}\n"
  end
end

def calculate_shared_variables(y_array)
  @n = y_array.length
  @sum_y = Float(y_array.sum)
  @sum_x = ((@n - 1) * @n) / 2
  @sum_x_squared = Float(y_array.each_with_index.map { |_y, x| x**2 }.sum)
  @sum_y_squared = Float(y_array.map { |y| y**2 }.sum)
  @sum_x_times_y = Float(y_array.each_with_index.map { |y, x| x * y }.sum)
end

def calculate_median(y_array)
  y_array[(y_array.length / 2).floor]
end

def calculate_mean
  @sum_y / @n.round(2)
end

def calculate_trendline
  ((@n * @sum_x_times_y - @sum_x * @sum_y) / (@n * @sum_x_squared - @sum_x**2)).round(2)
end

def calculate_trendline_accuracy
  if (@n * @sum_x_times_y - @sum_x * @sum_y) == 0
    r = 0
  else
    r = ((@n * @sum_x_times_y - @sum_x * @sum_y) / Math.sqrt((@n * @sum_x_squared - @sum_x**2) * (@n * @sum_y_squared - @sum_y**2))).round(4)
  end

  output = ''
  output += case r.abs
            when (0.90..1)
              'Very Strong'
            when (0.67..0.89)
              'Strong'
            when (0.33..0.66)
              'Moderate'
            else
              'Weak'
            end

  output += "(r = #{r})"
end
